package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SectionCreationMessage;
import buptspirit.spm.message.SectionEditingMessage;
import buptspirit.spm.message.SectionMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ChapterEntity;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.FileSourceEntity;
import buptspirit.spm.persistence.entity.SectionEntity;
import buptspirit.spm.persistence.entity.SectionFileEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.FileSourceFacade;
import buptspirit.spm.persistence.facade.SectionFacade;
import buptspirit.spm.persistence.facade.SectionFileFacade;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;
import java.util.List;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;
import static buptspirit.spm.persistence.JpaUtility.transactional;

public class SectionLogic {
    @Inject
    private ChapterFacade chapterFacade;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private SectionFacade sectionFacade;

    @Inject
    private MessageMapper messageMapper;

    @Inject
    private FileSourceFacade fileSourceFacade;

    @Inject
    private SectionFileFacade sectionFileFacade;

    @Inject
    private StaticFileLogic staticFileLogic;

    public SectionMessage insertSection(int courseId, byte chapterSequence, SectionCreationMessage sectionCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException, ServiceException {
        sectionCreationMessage.enforce();
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.POST_SECTION_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        ChapterEntity chapterEntity = transactional(
                em -> chapterFacade.findCourseChapterByCourseIdAndSequence(em, courseId, chapterSequence),
                "fail to find chapter");
        if (chapterEntity == null)
            throw ServiceError.POST_SECTION_CHAPTER_DO_NOT_EXISTS.toException();
        List<SectionEntity> chapterSections = transactional(
                em -> sectionFacade.findCourseChapterSections(em, chapterEntity.getChapterId()),
                "failed to find sections");
        byte sequence = sectionCreationMessage.getSequence();
        int size = chapterSections.size();
        if (sequence < 0 || sequence > size)
            throw ServiceError.POST_SECTION_CAN_NOT_BE_INSERTED.toException();
        else {
            SectionEntity newSection = new SectionEntity();
            newSection.setChapterId(chapterEntity.getChapterId());
            newSection.setSectionName(sectionCreationMessage.getSectionName());
            newSection.setSequence(sectionCreationMessage.getSequence());
            if (sequence == size) {
                return transactional(
                        em -> {
                            sectionFacade.create(em, newSection);
                            return messageMapper.intoSectionMessage(em, newSection);
                        },
                        "fail to create section"
                );
            } else {
                for (int i = sequence; i < size; i++) {
                    SectionEntity entity = chapterSections.get(i);
                    entity.setSequence((byte) (entity.getSequence() + 1));
                    transactional(
                            em -> {
                                sectionFacade.edit(em, entity);
                                return null;
                            },
                            "fail to edit"
                    );
                }
                return transactional(
                        em -> {
                            sectionFacade.create(em, newSection);
                            return messageMapper.intoSectionMessage(em, newSection);
                        },
                        "fail to create section"
                );
            }
        }
    }

    public SectionMessage editSection(int courseId, byte chapterSequence, byte sectionSequence, SectionEditingMessage sectionEditingMessage, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity courseEntity = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (courseEntity.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        ChapterEntity thisChapter = transactional(
                em -> chapterFacade.findCourseChapterByCourseIdAndSequence(em, courseId, chapterSequence),
                "failed to find chapter"
        );
        if (thisChapter == null)
            throw ServiceError.PUT_SECTION_NO_SUCH_CHAPTER.toException();
        SectionEntity thisSection = transactional(
                em -> sectionFacade.findCourseChapterSectionByChapterIdAndSequence(em, thisChapter.getChapterId(), sectionSequence),
                "failed to find this section"
        );
        if (thisSection == null)
            throw ServiceError.PUT_SECTION_NO_SUCH_SECTION.toException();
        thisSection.setSectionName(sectionEditingMessage.getSectionName());
        return transactional(
                em -> {
                    sectionFacade.edit(em, thisSection);
                    return messageMapper.intoSectionMessage(em, thisSection);
                },
                "failed to edit this section"
        );
    }

    public void deleteSection(int courseId, byte chapterSequence, byte sectionSequence, SessionMessage sessionMessage) throws ServiceException, ServiceAssertionException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse == null)
            throw ServiceError.DELETE_SECTION_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        ChapterEntity thisChapter = transactional(
                em -> chapterFacade.findCourseChapterByCourseIdAndSequence(em, courseId, chapterSequence),
                "failed to find chapter"
        );
        if (thisChapter == null)
            throw ServiceError.PUT_SECTION_NO_SUCH_CHAPTER.toException();
        List<SectionEntity> chapterSections = transactional(
                em -> sectionFacade.findCourseChapterSections(em, thisChapter.getChapterId()),
                "failed to find sections");
        serviceAssert(sectionSequence >= 0 && sectionSequence < chapterSections.size());
        transactional(
                em -> {
                    for (int i = sectionSequence + 1; i < chapterSections.size(); i++) {
                        SectionEntity section = chapterSections.get(i);
                        section.setSequence((byte) (section.getSequence() - 1));
                        sectionFacade.edit(em, section);
                    }
                    SectionEntity thisSection = chapterSections.get(sectionSequence);
                    sectionFacade.remove(em, thisSection);
                    return null;
                },
                "failed to delete this section"
        );
    }

    public void addFileToSection(int courseId, byte chapterSequence, byte sectionSequence, String fileIdentifier, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity courseEntity = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (courseEntity == null)
            throw ServiceError.PUT_SECTION_FILE_NO_SUCH_COURSE.toException();
        if (courseEntity.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        SectionEntity sectionEntity = transactional(
                em -> sectionFacade.findCourseChapterSection(em, courseId, chapterSequence, sectionSequence),
                "failed to find section"
        );
        if (sectionEntity == null)
            throw ServiceError.PUT_SECTION_FILE_NO_SUCH_SECTION.toException();
        FileSourceEntity fileSourceEntity = transactional(
                em -> fileSourceFacade.findByIdentifier(em, fileIdentifier),
                "failed to find file source"
        );
        if (fileSourceEntity == null)
            throw ServiceError.PUT_SECTION_FILE_NO_SUCH_FILE.toException();
        transactional(
                em -> {
                    SectionFileEntity sectionFile = new SectionFileEntity();
                    sectionFile.setFileSourceId(fileSourceEntity.getFileSourceId());
                    sectionFile.setSectionId(sectionEntity.getSectionId());
                    sectionFileFacade.create(em, sectionFile);
                    return null;
                },
                "failed to insert section file"
        );
    }

    public void deleteSectionFile(int courseId, byte chapterSequence, byte sectionSequence, String fileIdentifier, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity courseEntity = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (courseEntity == null)
            throw ServiceError.DELETE_SECTION_FILE_NO_SUCH_COURSE.toException();
        if (courseEntity.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        SectionFileEntity sectionFileEntity = transactional(
                em ->
                        sectionFileFacade.findSectionFileBySequenceAndIdentifier(
                                em,
                                courseId,
                                chapterSequence,
                                sectionSequence,
                                fileIdentifier
                        ),
                "failed to file section file"
        );
        if (sectionFileEntity == null)
            throw ServiceError.DELETE_SECTION_FILE_NO_SUCH_FILE.toException();
        transactional(
                em -> {
                    sectionFileFacade.remove(em, sectionFileEntity);
                    return null;
                },
                "failed to remote section file"
        );
        staticFileLogic.delete(fileIdentifier);
    }
}

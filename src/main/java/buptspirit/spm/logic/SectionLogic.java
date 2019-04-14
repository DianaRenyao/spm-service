package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import buptspirit.spm.persistence.entity.ChapterEntity;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.SectionEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SectionFacade;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<SectionMessage> getChapterSections(int courseId, int chapterId) throws ServiceException {
        boolean exists = transactional(
                em -> chapterFacade.find(em, chapterId) != null,
                "fail to find chapter");
        if (!exists)
            throw ServiceError.POST_STUDENT_USERNAME_ALREADY_EXISTS.toException();
        exists = transactional(
                em -> courseFacade.find(em, courseId) != null,
                "fail to find course"
        );
        if (!exists)
            throw ServiceError.POST_SECTION_COURSE_DO_NOT_EXISTS.toException();
        return transactional(
                em -> sectionFacade.findCourseChapterSections(em, chapterId).stream().map(
                        section -> messageMapper.intoSectionMessage(em, section)
                ).collect(Collectors.toList()),
                "fail to get course chapter section"
        );
    }

    public SectionMessage eidtSection(int courseId, byte chapterSequence, byte sectionSequence, SectionEditingMessage sectionEditingMessage, SessionMessage sessionMessage) throws ServiceException {
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

    public void deleteSection(int courseId, int chapterId, int sequence, SessionMessage sessionMessage) throws ServiceException, ServiceAssertionException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse == null)
            throw ServiceError.DELETE_SECTION_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        List<SectionEntity> chapterSections = transactional(
                em -> sectionFacade.findCourseChapterSections(em, chapterId),
                "failed to find sections");
        serviceAssert(sequence >= 0 && sequence < chapterSections.size());
        transactional(
                em -> {
                    for (int i = sequence + 1; i < chapterSections.size(); i++) {
                        SectionEntity section = chapterSections.get(i);
                        section.setSequence((byte) (section.getSequence() - 1));
                        sectionFacade.edit(em, section);
                    }
                    SectionEntity thisSection = chapterSections.get(sequence);
                    sectionFacade.remove(em, thisSection);
                    return null;
                },
                "failed to delete this section"
        );
    }
}

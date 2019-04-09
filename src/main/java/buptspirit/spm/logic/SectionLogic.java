package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SectionCreationMessage;
import buptspirit.spm.message.SectionMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.SectionEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SectionFacade;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class SectionLogic {
    @Inject
    ChapterFacade chapterFacade;

    @Inject
    CourseFacade courseFacade;

    @Inject
    SectionFacade sectionFacade;

    @Inject
    private MessageMapper messageMapper;

    public SectionMessage insertSection(int courseId, int chapterId, SectionCreationMessage sectionCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException, ServiceException {
        sectionCreationMessage.enforce();
        boolean exits = transactional(
                em -> chapterFacade.find(em, chapterId) != null,
                "fail to find chapter");
        if (!exits)
            throw ServiceError.POST_STUDENT_USERNAME_ALREADY_EXISTS.toException();
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.POST_SECTION_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        List<SectionEntity> chapterSections = transactional(
                em -> sectionFacade.findCourseChapterSections(em, chapterId),
                "fail to find sections");
        byte sequence = sectionCreationMessage.getSequence();
        int size = chapterSections.size();
        if (sequence < 0 || sequence > size)
            throw ServiceError.POST_SECTION_CAN_NOT_BE_INSERTED.toException();
        else if (sequence == size) {
            SectionEntity newSection = new SectionEntity();
            newSection.setChapterId(chapterId);
            newSection.setSectionName(sectionCreationMessage.getSectionName());
            newSection.setSequence(sectionCreationMessage.getSequence());
            return transactional(
                    em -> {
                        sectionFacade.create(em, newSection);
                        return messageMapper.intoSectionMessage(em, newSection);
                    },
                    "fail to create section"
            );
        } else {
            SectionEntity newSection = new SectionEntity();
            newSection.setChapterId(chapterId);
            newSection.setSectionName(sectionCreationMessage.getSectionName());
            newSection.setSequence(sectionCreationMessage.getSequence());
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

    public List<SectionMessage> getChapterSections(int courseId, int chapterId) {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        return transactional(
                em -> sectionFacade.findCourseChapterSections(em, chapterId).stream().map(
                        section -> messageMapper.intoSectionMessage(em, section)
                ).collect(Collectors.toList()),
                "fail to get course chapter section"
        );
    }
}

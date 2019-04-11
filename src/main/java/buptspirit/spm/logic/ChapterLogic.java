package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.ChapterCreationMessage;
import buptspirit.spm.message.ChapterMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ChapterEntity;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ChapterLogic {
    @Inject
    private ChapterFacade chapterFacade;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private MessageMapper messageMapper;

    public ChapterMessage insertChapter(int courseId, ChapterCreationMessage chapterCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException, ServiceException {
        chapterCreationMessage.enforce();
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.POST_CHAPTER_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        List<ChapterEntity> courseChapters = transactional(
                em -> chapterFacade.findCourseChapters(em, courseId),
                "fail to find any chapters"
        );
        byte sequence = chapterCreationMessage.getSequence();
        int size = courseChapters.size();
        if (sequence > size || sequence < 0)
            throw ServiceError.POST_CHAPTER_CAN_NOT_BE_INSERTED.toException();
        else {
            ChapterEntity newChapter = new ChapterEntity();
            newChapter.setChapterName(chapterCreationMessage.getChapterName());
            newChapter.setCourseId(courseId);
            newChapter.setSequence(chapterCreationMessage.getSequence());
            if (sequence == size) {
                return transactional(
                        em -> {
                            chapterFacade.create(em, newChapter);
                            return messageMapper.intoChapterMessage(em, newChapter);
                        },
                        "fail to create chapter"
                );
            } else {
                for (int i = sequence; i < courseChapters.size(); i++) {
                    ChapterEntity entity = courseChapters.get(i);
                    entity.setSequence((byte) (entity.getSequence() + 1));
                    transactional(
                            em -> {
                                chapterFacade.edit(em, entity);
                                return null;
                            },
                            "fail to edit this chapter"
                    );
                }
                return transactional(
                        em -> {
                            chapterFacade.create(em, newChapter);
                            return messageMapper.intoChapterMessage(em, newChapter);
                        },
                        "fail to create chapter"
                );
            }
        }
    }

    public List<ChapterMessage> getCourseChapters(int courseId) throws ServiceException {
        boolean exists = transactional(
                em -> courseFacade.find(em, courseId) != null,
                "fail to find course"
        );
        if (!exists)
            throw ServiceError.POST_CHAPTER_COURSE_DO_NOT_EXISTS.toException();
        return transactional(
                em -> chapterFacade.findCourseChapters(em, courseId).stream().map(
                        chapter -> messageMapper.intoChapterMessage(em, chapter))
                        .collect(Collectors.toList()),
                "fail to find any chapters"
        );
    }
}

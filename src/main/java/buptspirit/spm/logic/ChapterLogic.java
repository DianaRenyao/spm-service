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
import buptspirit.spm.persistence.entity.SectionEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SectionFacade;
import buptspirit.spm.rest.filter.Role;
import org.apache.logging.log4j.Logger;

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

    @Inject
    private SectionFacade sectionFacade;

    @Inject
    private Logger logger;

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

    public ChapterMessage editChapter(ChapterMessage chapterMessage, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, chapterMessage.getCourseId()),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.PUT_CHAPTER_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        ChapterEntity thisChapter = transactional(
                em -> chapterFacade.find(em, chapterMessage.getChapterId()),
                "fail to find this chapter"
        );
        thisChapter.setChapterName(chapterMessage.getChapterName());
        return transactional(
                em -> {
                    chapterFacade.edit(em, thisChapter);
                    return messageMapper.intoChapterMessage(em, thisChapter);
                },
                "fail to edit this chapter"
        );
    }

    public void deleteChapter(ChapterMessage chapterMessage, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, chapterMessage.getCourseId()),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.DELETE_CHAPTER_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        List<ChapterEntity> courseChapters = transactional(
                em -> chapterFacade.findCourseChapters(em, chapterMessage.getCourseId()),
                "fail to find any chapters"
        );
        /* 将删除的chapter后面的chapter的sequence都-1 */
        for (int i = chapterMessage.getSequence() + 1; i < courseChapters.size(); i++) {
            ChapterEntity chapter = courseChapters.get(i);
            chapter.setSequence((byte) (chapter.getSequence() - 1));
            transactional(
                    em -> {
                        chapterFacade.edit(em, chapter);
                        return null;
                    },
                    "fail to edit this chapter"
            );
        }
        ChapterEntity thisChapter = transactional(
                em -> chapterFacade.find(em, chapterMessage.getChapterId()),
                "fail to find this chapter"
        );
        for (int i = 0; i < chapterMessage.getSections().size(); i++) {
            int sectionId = chapterMessage.getSections().get(i).getSectionId();
            SectionEntity section = transactional(
                    em -> sectionFacade.find(em, sectionId)
                    ,
                    "fail to find section"
            );
            transactional(
                    em -> {
                        sectionFacade.remove(em, section);
                        return null;
                    },
                    "fail to remove section"
            );
        }
        transactional(
                em -> {
                    chapterFacade.remove(em, thisChapter);
                    return null;
                },
                "fail to remove this chapter"
        );
    }
}

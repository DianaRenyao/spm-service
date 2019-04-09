package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.ChapterCreationMessage;
import buptspirit.spm.message.ChapterMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.persistence.entity.ChapterEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;

import javax.inject.Inject;
import java.util.List;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ChapterLogic {
    @Inject
    ChapterFacade chapterFacade;

    @Inject
    private MessageMapper messageMapper;

    public ChapterMessage insertChapter(int courseId, ChapterCreationMessage chapterCreationMessage) throws ServiceAssertionException, ServiceException {
        chapterCreationMessage.enforce();
        List<ChapterEntity> courseChapters = transactional(
                em -> chapterFacade.findCourseChapters(em, courseId),
                "fail to find any chapters"
        );
        byte sequence = chapterCreationMessage.getSequence();
        int size = courseChapters.size();
        if (sequence > size || sequence < 0)
            throw ServiceError.POST_CHAPTER_CAN_NOT_BE_INSERTED.toException();
        else if (sequence == size) {
            ChapterEntity newChapter = new ChapterEntity();
            newChapter.setChapterName(chapterCreationMessage.getCourseName());
            newChapter.setCourseId(courseId);
            newChapter.setSequence(chapterCreationMessage.getSequence());
            return transactional(
                    em -> {
                        chapterFacade.create(em, newChapter);
                        return messageMapper.intoChapterMessage(em, newChapter);
                    },
                    "fail to create chapter"
            );
        } else {
            ChapterEntity newChapter = new ChapterEntity();
            newChapter.setChapterName(chapterCreationMessage.getCourseName());
            newChapter.setCourseId(courseId);
            newChapter.setSequence(chapterCreationMessage.getSequence());
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

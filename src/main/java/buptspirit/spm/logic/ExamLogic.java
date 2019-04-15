package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import buptspirit.spm.persistence.entity.*;
import buptspirit.spm.persistence.facade.*;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ExamLogic {
    @Inject
    private MessageMapper messageMapper;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private ChapterFacade chapterFacade;

    @Inject
    private ExamFacade examFacade;

    @Inject
    private QuestionFacade questionFacade;

    @Inject
    private QuestionOptionFacade questionOptionFacade;


    public ExamMessage createExam(int courseId, byte chapterSequence, SessionMessage sessionMessage, ExamCreationMessage examCreationMessage) throws ServiceAssertionException, ServiceException {
        examCreationMessage.enforce();
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.POST_EXAM_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        ChapterEntity thisChapter = transactional(
                em -> chapterFacade.findCourseChapterByCourseIdAndSequence(em, courseId, chapterSequence),
                "failed to find chapter"
        );
        if (thisChapter == null)
            throw ServiceError.POST_EXAM_CHAPTER_DO_NOT_EXISTS.toException();
        return transactional(
                em -> {
                    /* create exam entity*/
                    ExamEntity newExam = new ExamEntity();
                    newExam.setChapterId(thisChapter.getChapterId());
                    examFacade.create(em, newExam);
                    for (int i = 0; i < examCreationMessage.getQuestionsCreationMessages().size(); i++) {
                        /* create question entity*/
                        QuestionEntity newQuestion = new QuestionEntity();
                        QuestionCreationMessage questionCreationMessage = examCreationMessage.getQuestionsCreationMessages().get(i);
                        newQuestion.setDetail(questionCreationMessage.getDetail());
                        newQuestion.setExam(newExam.getExamId());
                        questionFacade.create(em, newQuestion);
                        int rightAnswerId = 0;
                        /* create question option entity */
                        for (int j = 0; j < questionCreationMessage.getQuestionOptionCreationMessages().size(); j++) {
                            QuestionOptionEntity newOption = new QuestionOptionEntity();
                            QuestionOptionCreationMessage questionOptionCreationMessage = questionCreationMessage.getQuestionOptionCreationMessages().get(j);
                            newOption.setQuestionId(newQuestion.getQuestionId());
                            newOption.setQuestionOptionDetail(questionOptionCreationMessage.getQuestionOptionDetail());
                            questionOptionFacade.create(em, newOption);
                            if (j == questionCreationMessage.getAnswerIndex())
                                rightAnswerId = newOption.getQuestionOptionId();
                        }
                        /* save answerId to question entity*/
                        newQuestion.setAnswer(rightAnswerId);
                        questionFacade.edit(em, newQuestion);
                    }
                    return messageMapper.intoExamMessage(em, newExam);
                },
                "failed to create exam"
        );
    }
}
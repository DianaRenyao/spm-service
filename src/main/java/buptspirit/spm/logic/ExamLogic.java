package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import buptspirit.spm.persistence.entity.*;
import buptspirit.spm.persistence.facade.*;
import buptspirit.spm.rest.filter.Role;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import org.apache.logging.log4j.Logger;
import java.util.stream.Collectors;

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

    @Inject
    private ExamScoreFacade examScoreFacade;

    @Inject
    private Logger logger;

    @Inject
    private SelectedCourseFacade selectedCourseFacade;


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
                    return messageMapper.intoExamMessage(em, newExam, true);
                },
                "failed to create exam"
        );
    }

    public List<TeacherExamSummaryMessage> getTeacherExamSummaries(int courseId, String username, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.GET_EXAM_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName())
                || thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId()
                || !username.equals(sessionMessage.getUserInfo().getUsername()))
            throw ServiceError.FORBIDDEN.toException();
        return transactional(
                em -> examFacade.findByCourseId(em, courseId).stream().map(
                        exam -> messageMapper.intoTeacherExamSummaryMessage(em, exam)
                ).collect(Collectors.toList()),
                "failed to find exams"
        );
    }

    public ExamScoreMessage verifyAnswers(int id, ExamAnswerMessage examAnswerMessage, SessionMessage sessionMessage) throws ServiceException {
        int examId = examAnswerMessage.getExamId();
        if(examId != id)
            throw ServiceError.POST_EXAM_ID_WRONG.toException();
        ChapterEntity chapterEntity = transactional(
                em -> chapterFacade.findByExamId(em, examId),
                "failed to find chapter"
        );
        if(chapterEntity == null)
            throw ServiceError.POST_EXAM_CHAPTER_DO_NOT_EXISTS.toException();
        boolean applied = transactional(
                em -> {
                    SelectedCourseEntityPK pk = new SelectedCourseEntityPK();
                    pk.setCourseCourseId(chapterEntity.getCourseId());
                    pk.setStudentUserId(sessionMessage.getUserInfo().getId());
                    return selectedCourseFacade.find(em, pk) != null;
                }, "failed to create exam score"
        );
        if (!applied)
            throw ServiceError.GET_EXAM_STUDENT_NOT_ALLOW.toException();
        List<QuestionEntity> questionEntities = transactional(
                em -> questionFacade.findByExamId(em, examId),
                "failed to find questions"
        );
        double questionsAmount = questionEntities.size();
        double correctAnswersAmount = 0.0;
        for (int j = 0; j < questionsAmount;j++) {
            int questionId = questionEntities.get(j).getQuestionId();
            QuestionEntity questionEntity = transactional(
                    em -> questionFacade.find(em, questionId),
                    "failed to find question"
            );
            QuestionAnswerMessage questionAnswerMessage = examAnswerMessage.getQuestionAnswerMessageList().get(j);
            if (questionAnswerMessage.getQuestionOptionId() == questionEntity.getAnswer()) {
                correctAnswersAmount++;
            }
        }
        double tempTotalScore=correctAnswersAmount / questionsAmount;
        logger.debug(tempTotalScore);
        int totalScore = (int)(tempTotalScore * 100);
        logger.debug(totalScore);
        ExamScoreEntity examScoreEntity = new ExamScoreEntity();
        examScoreEntity.setExamId(examId);
        examScoreEntity.setExamScore(totalScore);
        examScoreEntity.setSelectedCourseCourseCourseId(chapterEntity.getCourseId());
        examScoreEntity.setSelectedCourseStudentUserId(sessionMessage.getUserInfo().getId());
        return transactional(
                em -> {
                    examScoreFacade.create(em, examScoreEntity);
                    return messageMapper.intoExamScoreMessage(em, examScoreEntity);
                },
                "failed to create exam score"
        );
    }

    public List<StudentExamSummaryMessage> getStudentExamSummaries(int courseId, String username, SessionMessage sessionMessage) throws ServiceException {
        logger.debug(username);
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        logger.debug(sessionMessage.getUserInfo().getUsername());
        if (thisCourse == null)
            throw ServiceError.POST_EXAM_COURSE_DO_NOT_EXISTS.toException();
        if (!username.equals(sessionMessage.getUserInfo().getUsername()))
            throw ServiceError.FORBIDDEN.toException();
        logger.debug(sessionMessage.getUserInfo().getUsername());
        boolean applied = transactional(
                em -> {
                    SelectedCourseEntityPK pk = new SelectedCourseEntityPK();
                    pk.setCourseCourseId(courseId);
                    pk.setStudentUserId(sessionMessage.getUserInfo().getId());
                    return selectedCourseFacade.find(em, pk) != null;
                },
                "failed to find selected course entity"
        );
        if (!applied)
            throw ServiceError.GET_EXAM_STUDENT_NOT_ALLOW.toException();
        return transactional(
                em -> examFacade.findByCourseId(em, courseId).stream().map(
                        exam -> messageMapper.intoStudentExamSummaryMessage(em, exam, sessionMessage.getUserInfo().getId())
                ).collect(Collectors.toList()),
                "failed to find exams"
        );
    }

    public ExamMessage getExam(int examId, SessionMessage sessionMessage) {
        boolean withAnswer = sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName());
        return transactional(
                em -> {
                    ExamEntity examEntity = examFacade.find(em, examId);
                    return messageMapper.intoExamMessage(em, examEntity, withAnswer);
                },
                "failed to find this exam"
        );
    }
}


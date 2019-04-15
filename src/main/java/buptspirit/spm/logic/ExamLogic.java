package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.*;
import buptspirit.spm.persistence.entity.*;
import buptspirit.spm.persistence.facade.*;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;
import java.util.List;
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

    public List<TeacherExamSummaryMessage> getTeacherExamSummaries(int courseId, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.GET_EXAM_COURSE_DO_NOT_EXISTS.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName())
                || thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        return transactional(
                em -> examFacade.findByCourseId(em, courseId).stream().map(
                        exam -> messageMapper.intoTeacherExamSummaryMessage(em, exam)
                ).collect(Collectors.toList()),
                "failed to find exams"
        );
    }

    public List<StudentExamSummaryMessage> getStudentExamSummaries(int courseId, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "fail to find course"
        );
        if (thisCourse == null)
            throw ServiceError.POST_EXAM_COURSE_DO_NOT_EXISTS.toException();
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

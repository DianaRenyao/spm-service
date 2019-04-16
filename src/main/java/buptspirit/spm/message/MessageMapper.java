package buptspirit.spm.message;


import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.ChapterEntity;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.ExamEntity;
import buptspirit.spm.persistence.entity.ExamScoreEntity;
import buptspirit.spm.persistence.entity.ExamScoreEntityPK;
import buptspirit.spm.persistence.entity.ExperimentEntity;
import buptspirit.spm.persistence.entity.FileSourceEntity;
import buptspirit.spm.persistence.entity.NoticeEntity;
import buptspirit.spm.persistence.entity.QuestionEntity;
import buptspirit.spm.persistence.entity.QuestionOptionEntity;
import buptspirit.spm.persistence.entity.SectionEntity;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import buptspirit.spm.persistence.entity.StudentEntity;
import buptspirit.spm.persistence.entity.TeacherEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.ExamScoreFacade;
import buptspirit.spm.persistence.facade.ExperimentFacade;
import buptspirit.spm.persistence.facade.FileSourceFacade;
import buptspirit.spm.persistence.facade.QuestionFacade;
import buptspirit.spm.persistence.facade.QuestionOptionFacade;
import buptspirit.spm.persistence.facade.SectionFacade;
import buptspirit.spm.persistence.facade.StudentFacade;
import buptspirit.spm.persistence.facade.TeacherFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MessageMapper {

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private TeacherFacade teacherFacade;

    @Inject
    private StudentFacade studentFacade;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private SectionFacade sectionFacade;

    @Inject
    private ChapterFacade chapterFacade;

    @Inject
    private FileSourceFacade fileSourceFacade;

    @Inject
    private ExperimentFacade experimentFacade;

    @Inject
    private QuestionOptionFacade questionOptionFacade;

    @Inject
    private QuestionFacade questionFacade;

    @Inject
    private ExamScoreFacade examScoreFacade;

    public NoticeMessage intoNoticeMessage(EntityManager em, NoticeEntity entity) {
        int authorId = entity.getAuthor();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, authorId));
        return NoticeMessage.fromEntity(entity, teacher);
    }

    public TeacherMessage intoTeacherMessage(EntityManager em, TeacherEntity entity) {
        int userId = entity.getUserId();
        UserInfoMessage user = intoUserInfoMessage(em, userInfoFacade.find(em, userId));
        return TeacherMessage.fromEntity(entity, user);
    }

    public UserInfoMessage intoUserInfoMessage(EntityManager em, UserInfoEntity entity) {
        return UserInfoMessage.fromEntity(entity);
    }

    public StudentMessage intoStudentMessage(EntityManager em, StudentEntity entity) {
        int userId = entity.getUserId();
        UserInfoMessage user = intoUserInfoMessage(em, userInfoFacade.find(em, userId));
        return StudentMessage.fromEntity(entity, user);
    }

    public SelectedCourseMessage intoScoreMessage(EntityManager em, SelectedCourseEntity entity) {
        int studentId = entity.getStudentUserId();
        StudentEntity student = studentFacade.find(em, studentId);
        StudentMessage studentMessage = intoStudentMessage(em, student);
        int courseId = entity.getCourseCourseId();
        CourseEntity course = courseFacade.find(em, courseId);
        CourseSummaryMessage courseMessage = intoCourseSummaryMessage(em, course);
        return SelectedCourseMessage.fromEntity(entity, studentMessage, courseMessage);
    }

    public CourseMessage intoCourseMessage(EntityManager em, CourseEntity entity) {
        int userId = entity.getTeacherUserId();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, userId));
        int courseId = entity.getCourseId();
        List<ChapterMessage> chapters = chapterFacade.findCourseChapters(em, courseId).stream().map(
                chapter -> intoChapterMessage(em, chapter)).collect(Collectors.toList());
        return CourseMessage.fromEntity(entity, teacher, chapters);
    }

    public CourseSummaryMessage intoCourseSummaryMessage(EntityManager em, CourseEntity entity) {
        int userId = entity.getTeacherUserId();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, userId));
        return CourseSummaryMessage.fromEntity(entity, teacher);
    }

    public ApplicationMessage intoApplicationMessage(EntityManager em, ApplicationEntity entity) {
        int studentUserId = entity.getStudentUserId();
        StudentMessage student = intoStudentMessage(em, studentFacade.find(em, studentUserId));
        int courseId = entity.getCourseId();
        CourseSummaryMessage courseMessage = intoCourseSummaryMessage(em, courseFacade.find(em, courseId));
        return ApplicationMessage.fromEntity(entity, courseMessage, student);
    }

    public ChapterMessage intoChapterMessage(EntityManager em, ChapterEntity entity) {
        int chapterId = entity.getChapterId();
        List<SectionMessage> sections = sectionFacade.findCourseChapterSections(em, chapterId).stream().map(
                section -> intoSectionMessage(em, section)).collect(Collectors.toList());
        return ChapterMessage.fromEntity(entity, sections);
    }

    public SectionMessage intoSectionMessage(EntityManager em, SectionEntity entity) {
        int sectionId = entity.getSectionId();
        List<FileSourceMessage> fileSources = fileSourceFacade.findSectionFile(em, sectionId)
                .map(file -> intoFileSourceMessage(em, file)).collect(Collectors.toList());
        return SectionMessage.fromEntity(entity, fileSources);
    }

    public FileSourceMessage intoFileSourceMessage(EntityManager em, FileSourceEntity entity) {
        return FileSourceMessage.fromEntity(entity);
    }


    public ExperimentMessage intoExperimentMessage(EntityManager em, ExperimentCreationMessage experimentCreationMessage) {
        ExperimentEntity experimentEntity = experimentFacade.findByName(em, experimentCreationMessage.getExperimentName());
        return ExperimentMessage.fromEntity(experimentEntity);
    }

    public ExperimentMessage intoExperimentMessage(EntityManager em, ExperimentEntity experiment) {
        return ExperimentMessage.fromEntity(experiment);
    }

    public QuestionOptionMessage intoQuestionOptionMessage(EntityManager em, QuestionOptionEntity entity) {
        return QuestionOptionMessage.fromEntitiy(entity);
    }

    public QuestionMessage intoQuestionMessage(EntityManager em, QuestionEntity entity, boolean withAnswer) {
        int questionId = entity.getQuestionId();
        List<QuestionOptionMessage> questionOptionMessages = questionOptionFacade.findByQuestionId(em, questionId)
                .stream().map(questionOption -> intoQuestionOptionMessage(em, questionOption)).collect(Collectors.toList());
        return QuestionMessage.fromEntity(entity, questionOptionMessages, withAnswer);
    }

    public ExamMessage intoExamMessage(EntityManager em, ExamEntity entity, boolean withAnswer) {
        int examId = entity.getExamId();
        List<QuestionMessage> questionMessages = questionFacade.findByExamId(em, examId)
                .stream().map(question -> intoQuestionMessage(em, question, withAnswer)).collect(Collectors.toList());
        return ExamMessage.fromEntity(entity, questionMessages);
    }

    public ChapterSummaryMessage intoChapterSummaryMessage(EntityManager em, ChapterEntity entity) {
        return ChapterSummaryMessage.fromEntity(entity);
    }

    public TeacherExamSummaryMessage intoTeacherExamSummaryMessage(EntityManager em, ExamEntity entity) {
        int chapterId = entity.getChapterId();
        ChapterEntity chapter = chapterFacade.find(em, chapterId);
        ChapterSummaryMessage chapterSummaryMessage = intoChapterSummaryMessage(em, chapter);
        int examId = entity.getExamId();
        List<QuestionEntity> questionEntities = questionFacade.findByExamId(em, examId);
        int questionNum = questionEntities.size();
        return TeacherExamSummaryMessage.fromEntity(entity, chapterSummaryMessage, questionNum);
    }

    public ExamScoreMessage intoExamScoreMessage(EntityManager em, ExamScoreEntity entity) {
        return ExamScoreMessage.fromEntity(entity);
    }

    public StudentExamSummaryMessage intoStudentExamSummaryMessage(EntityManager em, ExamEntity entity, int studentId) {
        int chapterId = entity.getChapterId();
        ChapterEntity chapter = chapterFacade.find(em, chapterId);
        ChapterSummaryMessage chapterSummaryMessage = intoChapterSummaryMessage(em, chapter);
        int examId = entity.getExamId();
        List<QuestionEntity> questionEntities = questionFacade.findByExamId(em, examId);
        int questionNum = questionEntities.size();

        /* get student current score*/
        ExamScoreEntityPK pk = new ExamScoreEntityPK();
        pk.setExamId(entity.getExamId());
        pk.setSelectedCourseCourseCourseId(chapter.getCourseId());
        pk.setSelectedCourseStudentUserId(studentId);
        ExamScoreEntity examScoreEntity = examScoreFacade.find(em, pk);
        ExamScoreMessage examScoreMessage = new ExamScoreMessage();
        if (examScoreEntity == null) {
            examScoreMessage.setExamScore(null);
            examScoreMessage.setExamId(entity.getExamId());
            examScoreMessage.setStudentId(studentId);
        } else
            examScoreMessage = intoExamScoreMessage(em, examScoreEntity);

        return StudentExamSummaryMessage.fromEntity(entity, chapterSummaryMessage, questionNum, examScoreMessage);
    }
}

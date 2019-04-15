package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.*;
import buptspirit.spm.persistence.facade.*;

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
    private QuestionOptionFacade questionOptionFacade;

    @Inject
    private QuestionFacade questionFacade;

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
        CourseSummaryMessage courseMessage= intoCourseSummaryMessage(em, course);
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

    public QuestionOptionMessage intoQuestionOptionMessage(EntityManager em, QuestionOptionEntity entity) {
        return QuestionOptionMessage.fromEntitiy(entity);
    }

    public QuestionMessage intoQuestionMessage(EntityManager em, QuestionEntity entity) {
        int questionId = entity.getQuestionId();
        List<QuestionOptionMessage> questionOptionMessages = questionOptionFacade.findByQuestionId(em, questionId)
                .stream().map(questionOption -> intoQuestionOptionMessage(em, questionOption)).collect(Collectors.toList());
        return QuestionMessage.fromEntity(entity, questionOptionMessages);
    }

    public ExamMessage intoExamMessage(EntityManager em, ExamEntity entity) {
        int examId = entity.getExamId();
        List<QuestionMessage> questionMessages = questionFacade.findByExamId(em, examId)
                .stream().map(question -> intoQuestionMessage(em, question)).collect(Collectors.toList());
        return ExamMessage.fromEntity(entity, questionMessages);
    }
}

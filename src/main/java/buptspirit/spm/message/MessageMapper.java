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
        return SectionMessage.fromEntity(entity);
    }
}

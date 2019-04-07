package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.*;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.NoticeEntity;
import buptspirit.spm.persistence.entity.StudentEntity;
import buptspirit.spm.persistence.entity.TeacherEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.facade.TeacherFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import buptspirit.spm.persistence.facade.ScoreFacade;
import buptspirit.spm.persistence.facade.StudentFacade;
import buptspirit.spm.persistence.facade.CourseFacade;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class MessageMapper {

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private TeacherFacade teacherFacade;

    private StudentFacade studentFacade;

    @Inject
    private CourseFacade courseFacade;
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

    public ScoreMessage intoMessage(EntityManager em, SelectedCourseEntity entity) {
        int StudentId = entity.getStudentUserId();
        return ScoreMessage.fromEntity(entity);
    }

    public CourseMessage intoCourseMessage(EntityManager em, CourseEntity entity) {
        int userId = entity.getTeacherUserId();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, userId));
        return CourseMessage.fromEntity(entity, teacher);
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
        CourseMessage courseMessage = intoCourseMessage(em, courseFacade.find(em, courseId));
        return ApplicationMessage.fromEntity(entity, courseMessage,student);
    }
}

package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.*;
import buptspirit.spm.persistence.facade.TeacherFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class MessageMapper {

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private TeacherFacade teacherFacade;

    public NoticeMessage intoMessage(EntityManager em, NoticeEntity entity) {
        int authorId = entity.getAuthor();
        TeacherMessage teacher = intoMessage(em, teacherFacade.find(em, authorId));
        return NoticeMessage.fromEntity(entity, teacher);
    }

    public TeacherMessage intoMessage(EntityManager em, TeacherEntity entity) {
        int userId = entity.getUserId();
        UserInfoMessage user = intoMessage(em, userInfoFacade.find(em, userId));
        return TeacherMessage.fromEntity(entity, user);
    }

    public UserInfoMessage intoMessage(EntityManager em, UserInfoEntity entity) {
        return UserInfoMessage.fromEntity(entity);
    }

    public StudentMessage intoMessage(EntityManager em, StudentEntity entity) {
        int userId = entity.getUserId();
        UserInfoMessage user = intoMessage(em, userInfoFacade.find(em, userId));
        return StudentMessage.fromEntity(entity, user);
    }

    public CourseMessage intoMessage(EntityManager em, CourseEntity entity) {
        int userId = entity.getTeacherUserId();
        UserInfoMessage user = intoMessage(em, userInfoFacade.find(em, userId));
        return CourseMessage.fromEntity(entity, user);
    }
}

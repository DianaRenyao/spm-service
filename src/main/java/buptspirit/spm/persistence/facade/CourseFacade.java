package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class CourseFacade extends AbstractFacade<CourseEntity> {
    public CourseFacade() {
        super(CourseEntity.class);
    }

    public List<CourseEntity> findOptionalCourses(EntityManager em) {
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        return em.createQuery("select c from CourseEntity c where c.startDate >= :currentDate", CourseEntity.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<CourseEntity> findTeacherCourses(EntityManager em, UserInfoEntity teacher) {
        return em.createQuery("select c from CourseEntity c where c.teacherUserId >= :teacherUserId", CourseEntity.class)
                .setParameter("teacherUserId", teacher.getUserId())
                .getResultList();
    }
}

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

    public List<CourseEntity> findOptionalCourses(EntityManager em)
    {
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        try {
            return em.createQuery("select c from CourseEntity c where c.startDate >= :currentDate", CourseEntity.class)
                    .setParameter("currentDate", currentDate)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CourseEntity> findTeacherCourses(EntityManager em, UserInfoEntity teacher)
    {
        try {
            return em.createQuery("select c from CourseEntity c where c.teacherUserId >= :teacherUserId", CourseEntity.class)
                    .setParameter("teacherUserId", teacher.getUserId())
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}

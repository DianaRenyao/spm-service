package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.CourseEntity;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class CourseFacade extends AbstractFacade<CourseEntity> {
    public CourseFacade() {
        super(CourseEntity.class);
    }

    public List<CourseEntity> findSelectableCourses(EntityManager em) {
        Date currentDate = new Date(System.currentTimeMillis());
        return em.createQuery("select c from CourseEntity c where c.finishDate >= :currentDate", CourseEntity.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<CourseEntity> findTeacherCourses(EntityManager em, int teacherId) {
        return em.createQuery("select c from CourseEntity c where c.teacherUserId = :teacherUserId", CourseEntity.class)
                .setParameter("teacherUserId", teacherId)
                .getResultList();
    }

    public List<CourseEntity> findTeacherSelectableCourses(EntityManager em, int teacherId) {
        Date currentDate = new Date(System.currentTimeMillis());
        return em.createQuery("select c from CourseEntity c " +
                        "where c.teacherUserId = :teacherUserId and c.finishDate >= :currentDate",
                CourseEntity.class)
                .setParameter("teacherUserId", teacherId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }
}

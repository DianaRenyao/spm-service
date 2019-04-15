package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class SelectedCourseFacade extends AbstractFacade<SelectedCourseEntity> {
    SelectedCourseFacade() {
        super(SelectedCourseEntity.class);
    }

    public SelectedCourseEntity findByCourseIdAndStudentId(EntityManager em, int courseCourseId, int studentUserId) {
        try {
            return em.createQuery("select s from SelectedCourseEntity s where s.courseCourseId = :courseCourseId and s.studentUserId = :studentUserId", SelectedCourseEntity.class)
                    .setParameter("courseCourseId", courseCourseId)
                    .setParameter("studentUserId", studentUserId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<SelectedCourseEntity> findByStudentId(EntityManager em, int studentId) {
        return em.createQuery("select s from SelectedCourseEntity s where s.studentUserId = :studentUserId", SelectedCourseEntity.class)
                .setParameter("studentUserId", studentId)
                .getResultList();
    }


    public SelectedCourseEntity findByStudentUserIdAndCourseId(EntityManager em, int studentUserId, int courseCourseId) {
        String query = "select u from SelectedCourseEntity u " +
                "where u.studentUserId = :studentUserId and u.courseCourseId=:courseCourseId";
        try {
            return em.createQuery(query, SelectedCourseEntity.class)
                    .setParameter("studentUserId", studentUserId)
                    .setParameter("courseCourseId", courseCourseId)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public List<SelectedCourseEntity> findByCourseId(EntityManager em, int courseId) {
        String query = "select u from SelectedCourseEntity u " +
                "where u.courseCourseId = :courseId";
        try {
            return em.createQuery(query, SelectedCourseEntity.class)
                    .setParameter("courseId", courseId)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }
}

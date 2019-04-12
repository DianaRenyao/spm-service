package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class SelectedCourseFacade extends AbstractFacade<SelectedCourseEntity>{
    SelectedCourseFacade() {
        super(SelectedCourseEntity.class);
    }

    public SelectedCourseEntity findByCourseIdAndStudentId(EntityManager em, int courseCourseId, int studentUserId){
        try {
            return em.createQuery("select s from SelectedCourseEntity s where s.courseCourseId = :courseCourseId and s.studentUserId = :studentUserId", SelectedCourseEntity.class)
                    .setParameter("courseCourseId", courseCourseId)
                    .setParameter("studentUserId", studentUserId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<SelectedCourseEntity> findBystudentUserName(EntityManager em, String username) {
        String query = "select s from SelectedCourseEntity s, UserInfoEntity u " +
                "where s.studentUserId = u.userId and u.username=:username";
        try {
            return em.createQuery(query, SelectedCourseEntity.class)
                    .setParameter("username", username)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }

    public SelectedCourseEntity findBystudentId(EntityManager em, int studentUseId) {
        String query = "select s from SelectedCourseEntity s where s.studentUserId = :studentUseId";
        try {
            return em.createQuery(query, SelectedCourseEntity.class)
                    .setParameter("studentUseId", studentUseId)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public SelectedCourseEntity findBystudentUserIdAndCourseId(EntityManager em, int studentUserId, int courseCourseId) {
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

    public List<SelectedCourseEntity> findBycourseCourseId(EntityManager em, int courseCourseId) {
        String query = "select u from SelectedCourseEntity u " +
                "where u.courseCourseId = :courseCourseId";
        try {
            return em.createQuery(query, SelectedCourseEntity.class)
                    .setParameter("courseCourseId", courseCourseId)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }
}

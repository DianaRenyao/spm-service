package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Singleton
public class ScoreFacade extends AbstractFacade<SelectedCourseEntity> {

    public ScoreFacade() {
        super(SelectedCourseEntity.class);
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
}

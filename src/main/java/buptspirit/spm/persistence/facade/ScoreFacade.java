package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Singleton
public class ScoreFacade extends AbstractFacade<SelectedCourseEntity> {

    public ScoreFacade() {
        super(SelectedCourseEntity.class);
    }

    public List<SelectedCourseEntity> findBystudentUserId(EntityManager em, int studentUserId) {
        String query = "select u from SelectedCourseEntity u " +
                "where u.studentUserId = :studentUserId";
        try {
            return em.createQuery(query, SelectedCourseEntity.class)
                    .setParameter("studentUserId", studentUserId)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }

    public SelectedCourseEntity findBystudentUserIdAndCourseId(EntityManager em, int studentUserId,int courseCourseId) {
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

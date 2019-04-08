package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ApplicationEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class ApplicationFacade extends AbstractFacade<ApplicationEntity> {

    ApplicationFacade() {
        super(ApplicationEntity.class);
    }

    public ApplicationEntity findByCourseIdAndStudentId(EntityManager em, int courseId, int studentUserId){
        try {
            return em.createQuery("select a from ApplicationEntity a where a.courseId = :courseId and a.studentUserId = :studentUserId", ApplicationEntity.class)
                    .setParameter("courseId", courseId)
                    .setParameter("studentUserId", studentUserId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ApplicationEntity> findByCourseId(EntityManager em, int courseId){
        return em.createQuery("select a from ApplicationEntity a where a.courseId = :courseId ", ApplicationEntity.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}

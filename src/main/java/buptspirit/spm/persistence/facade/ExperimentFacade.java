package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ExperimentEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ExperimentFacade extends AbstractFacade<ExperimentEntity> {
    ExperimentFacade() {
        super(ExperimentEntity.class);
    }

    public ExperimentEntity findByName(EntityManager em, String experimentName) {
        try {
            return em.createQuery("select e from ExperimentEntity e " +
                    "where e.experimentName=:experimentName", ExperimentEntity.class)
                    .setParameter("experimentName", experimentName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

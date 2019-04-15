package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.QuestionEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class QuestionFacade extends AbstractFacade<QuestionEntity> {
    public QuestionFacade() {
        super(QuestionEntity.class);
    }

    public List<QuestionEntity> findByExamId(EntityManager em, int exam) {
        return em.createQuery("select q from QuestionEntity q where q.exam = :exam", QuestionEntity.class)
                .setParameter("exam", exam)
                .getResultList();
    }
}

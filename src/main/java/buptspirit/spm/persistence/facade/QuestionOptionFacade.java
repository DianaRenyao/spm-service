package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.QuestionOptionEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class QuestionOptionFacade extends AbstractFacade<QuestionOptionEntity> {
    public QuestionOptionFacade() {
        super(QuestionOptionEntity.class);
    }

    public List<QuestionOptionEntity> findByQuestionId(EntityManager em, int questionId) {
        return em.createQuery("select q from QuestionOptionEntity q where q.questionId = :questionId", QuestionOptionEntity.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }
}

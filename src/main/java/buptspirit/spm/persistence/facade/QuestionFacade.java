package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.QuestionEntity;

public class QuestionFacade extends AbstractFacade<QuestionEntity> {
    public QuestionFacade() {
        super(QuestionEntity.class);
    }
}

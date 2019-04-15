package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.QuestionOptionEntity;

public class QuestionOptionMessage {
    int questionId;
    int questionOptionId;
    String questionOptionDetail;

    public static QuestionOptionMessage fromEntitiy(QuestionOptionEntity questionOptionEntity) {
        QuestionOptionMessage questionOptionMessage = new QuestionOptionMessage();
        questionOptionMessage.setQuestionId(questionOptionEntity.getQuestionId());
        questionOptionMessage.setQuestionOptionId(questionOptionEntity.getQuestionOptionId());
        questionOptionMessage.setQuestionOptionDetail(questionOptionEntity.getQuestionOptionDetail());
        return questionOptionMessage;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(int questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public String getQuestionOptionDetail() {
        return questionOptionDetail;
    }

    public void setQuestionOptionDetail(String questionOptionDetail) {
        this.questionOptionDetail = questionOptionDetail;
    }
}

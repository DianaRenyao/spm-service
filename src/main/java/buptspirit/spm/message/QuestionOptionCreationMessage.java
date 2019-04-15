package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class QuestionOptionCreationMessage implements InputMessage {
    private int questionId;
    private String questionOptionDetail;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(questionId != 0);
        serviceAssert(questionOptionDetail != null && !questionOptionDetail.isEmpty());
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionOptionDetail() {
        return questionOptionDetail;
    }

    public void setQuestionOptionDetail(String questionOptionDetail) {
        this.questionOptionDetail = questionOptionDetail;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import java.util.List;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class QuestionCreationMessage implements InputMessage {

    private int examId;
    private String detail;
    private List<QuestionOptionCreationMessage> questionOptionCreationMessages;
    private int answerIndex;
    private int answerId;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(detail != null && !detail.isEmpty());
        serviceAssert(questionOptionCreationMessages != null && !questionOptionCreationMessages.isEmpty());
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<QuestionOptionCreationMessage> getQuestionOptionCreationMessages() {
        return questionOptionCreationMessages;
    }

    public void setQuestionOptionCreationMessages(List<QuestionOptionCreationMessage> questionOptionCreationMessages) {
        this.questionOptionCreationMessages = questionOptionCreationMessages;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}

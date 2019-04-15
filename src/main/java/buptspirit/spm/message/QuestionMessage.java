package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.QuestionEntity;

import java.util.List;

public class QuestionMessage {
    int examId;
    int questionId;
    String questionDetail;
    int answerId;
    List<QuestionOptionMessage> questionOptionMessages;

    public static QuestionMessage fromEntity(QuestionEntity entity, List<QuestionOptionMessage> questionOptionMessages) {
        QuestionMessage questionMessage = new QuestionMessage();
        questionMessage.setExamId(entity.getExam());
        questionMessage.setQuestionId(entity.getQuestionId());
        questionMessage.setQuestionDetail(entity.getDetail());
        questionMessage.setAnswerId(entity.getAnswer());
        questionMessage.setQuestionOptionMessages(questionOptionMessages);
        return questionMessage;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public List<QuestionOptionMessage> getQuestionOptionMessages() {
        return questionOptionMessages;
    }

    public void setQuestionOptionMessages(List<QuestionOptionMessage> questionOptionMessages) {
        this.questionOptionMessages = questionOptionMessages;
    }
}

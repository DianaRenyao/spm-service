package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ExamEntity;

import java.util.List;

public class ExamMessage {
    private int chapterId;
    private int examId;
    private List<QuestionMessage> questionMessages;

    public static ExamMessage fromEntity(ExamEntity entity, List<QuestionMessage> questionMessages) {
        ExamMessage examMessage = new ExamMessage();
        examMessage.setExamId(entity.getExamId());
        examMessage.setChapterId(entity.getChapterId());
        examMessage.setQuestionMessages(questionMessages);
        return examMessage;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public List<QuestionMessage> getQuestionMessages() {
        return questionMessages;
    }

    public void setQuestionMessages(List<QuestionMessage> questionMessages) {
        this.questionMessages = questionMessages;
    }
}

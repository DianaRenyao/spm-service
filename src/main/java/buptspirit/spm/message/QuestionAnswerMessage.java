package buptspirit.spm.message;

public class QuestionAnswerMessage {
    private int questionId;
    private int quesitionOptionId;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuesitionOptionId() {
        return quesitionOptionId;
    }

    public void setQuesitionOptionId(int quesitionOptionId) {
        this.quesitionOptionId = quesitionOptionId;
    }
}

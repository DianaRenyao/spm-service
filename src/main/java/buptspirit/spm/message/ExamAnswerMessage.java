package buptspirit.spm.message;

import java.util.List;

public class ExamAnswerMessage {
    private int examId;
    private List<QuestionAnswerMessage> questionAnswers;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public List<QuestionAnswerMessage> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswerMessage> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}

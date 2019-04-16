package buptspirit.spm.message;

import java.util.List;

public class ExamAnswerMessage {
    private int examId;
    private List<QuestionAnswerMessage> questionAnswerMessageList;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public List<QuestionAnswerMessage> getQuestionAnswerMessageList() {
        return questionAnswerMessageList;
    }

    public void setQuestionAnswerMessageList(List<QuestionAnswerMessage> questionAnswerMessageList) {
        this.questionAnswerMessageList = questionAnswerMessageList;
    }
}

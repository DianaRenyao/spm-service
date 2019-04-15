package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ExamEntity;
import buptspirit.spm.persistence.entity.ExamScoreEntity;

public class StudentExamSummaryMessage {

    private ChapterSummaryMessage chapter;
    private int questionNum;
    private ExamScoreMessage examScoreMessage ;

    public static StudentExamSummaryMessage fromEntity(ExamEntity entity,
                                                       ChapterSummaryMessage chapter,
                                                       int questionNum,
                                                       ExamScoreMessage examScoreMessage){
        StudentExamSummaryMessage studentExamSummaryMessage = new StudentExamSummaryMessage();
        studentExamSummaryMessage.setChapter(chapter);
        studentExamSummaryMessage.setQuestionNum(questionNum);
        studentExamSummaryMessage.setExamScoreMessage(examScoreMessage);
        return studentExamSummaryMessage;
    }

    public ChapterSummaryMessage getChapter() {
        return chapter;
    }

    public void setChapter(ChapterSummaryMessage chapter) {
        this.chapter = chapter;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public ExamScoreMessage getExamScoreMessage() {
        return examScoreMessage;
    }

    public void setExamScoreMessage(ExamScoreMessage examScoreMessage) {
        this.examScoreMessage = examScoreMessage;
    }
}

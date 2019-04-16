package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ExamEntity;

public class TeacherExamSummaryMessage {
    private ChapterSummaryMessage chapter;
    private int questionNum;
    private int examId;

    public static TeacherExamSummaryMessage fromEntity(ExamEntity entity, ChapterSummaryMessage chapter, int questionNum) {
        TeacherExamSummaryMessage teacherExamSummaryMessage = new TeacherExamSummaryMessage();
        teacherExamSummaryMessage.setChapter(chapter);
        teacherExamSummaryMessage.setExamId(entity.getExamId());
        teacherExamSummaryMessage.setQuestionNum(questionNum);
        return teacherExamSummaryMessage;
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

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}

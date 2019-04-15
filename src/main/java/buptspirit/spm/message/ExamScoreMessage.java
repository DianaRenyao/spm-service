package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ExamScoreEntity;

public class ExamScoreMessage {
    private int studentId;
    private int examId;
    private int examScore;

    public static ExamScoreMessage fromEntity(ExamScoreEntity entity){
        ExamScoreMessage examScoreMessage = new ExamScoreMessage();
        examScoreMessage.setExamId(entity.getExamId());
        examScoreMessage.setStudentId(entity.getSelectedCourseStudentUserId());
        examScoreMessage.setExamScore(entity.getExamScore());
        return examScoreMessage;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getExamScore() {
        return examScore;
    }

    public void setExamScore(int examScore) {
        this.examScore = examScore;
    }
}

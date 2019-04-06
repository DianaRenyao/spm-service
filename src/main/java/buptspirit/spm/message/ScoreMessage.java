package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ScoreMessage {

    private int studentUserId;
    private int courseCourseId;
    private Timestamp timeApproved;
    private BigDecimal avgOnlineScore;
    private BigDecimal midScore;
    private BigDecimal finalScore;
    private BigDecimal totalScore;

    public static ScoreMessage fromEntity(SelectedCourseEntity entity) {
        ScoreMessage message=new ScoreMessage();
        message.studentUserId=entity.getStudentUserId();
        message.courseCourseId=entity.getCourseCourseId();
        message.timeApproved=entity.getTimeApproved();
        message.avgOnlineScore=entity.getAvgOnlineScore();
        message.midScore=entity.getMidScore();
        message.finalScore=entity.getFinalScore();
        message.totalScore=entity.getTotalScore();

        return message;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public int getCourseCourseId() {
        return courseCourseId;
    }

    public void setCourseCourseId(int courseCourseId) {
        this.courseCourseId = courseCourseId;
    }

    public Timestamp getApproveTime() {
        return timeApproved;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.timeApproved = timeApproved;
    }

    public BigDecimal getAvgOnlineScore() {
        return avgOnlineScore;
    }

    public void setAvgOnlineScore(BigDecimal avgOnlineScore) {
        this.avgOnlineScore = avgOnlineScore;
    }

    public BigDecimal getMidScore() {
        return midScore;
    }

    public void setMidScore(BigDecimal midScore) {
        this.midScore = midScore;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }
}


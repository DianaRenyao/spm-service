package buptspirit.spm.message;
import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class ScoreCreateMessage {
    private int studentUserId;
    private int courseCourseId;
    private BigDecimal avgOnlineScore;
    private BigDecimal midScore;
    private BigDecimal finalScore;
    private Timestamp timeApproved;
    private BigDecimal totolScore;

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

    public Timestamp getTimeApproved() {
        return timeApproved;
    }

    public void setTimeApproved(Timestamp timeApproved) {
        this.timeApproved = timeApproved;
    }

    public BigDecimal getTotolScore() {
        return totolScore;
    }

    public void setTotolScore(BigDecimal totolScore) {
        this.totolScore = totolScore;
    }
}

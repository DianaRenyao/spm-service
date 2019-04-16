package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import java.math.BigDecimal;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class SelectedCourseEditingMessage implements InputMessage {

    private int studentUserId;
    private BigDecimal avgOnlineScore;
    private BigDecimal midScore;
    private BigDecimal finalScore;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(avgOnlineScore != null);
        serviceAssert(midScore != null);
        serviceAssert(finalScore != null);
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
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

}

package buptspirit.spm.message;
import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;

public class SelectedCourseCreationMessage {

    private int studentUserId;
    private BigDecimal avgOnlineScore;
    private BigDecimal midScore;
    private BigDecimal finalScore;
    private BigDecimal totalScore;

    public SelectedCourseCreationMessage(){}
    public SelectedCourseCreationMessage(int studentUserId, BigDecimal avgOnlineScore, BigDecimal midScore,
                                         BigDecimal finalScore, BigDecimal totalScore){
        this.avgOnlineScore=avgOnlineScore;
        this.finalScore=finalScore;
        this.midScore=midScore;
        this.studentUserId=studentUserId;
        this.totalScore=totalScore;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
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

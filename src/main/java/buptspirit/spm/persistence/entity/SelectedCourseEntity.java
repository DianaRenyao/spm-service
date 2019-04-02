package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "selected_course", schema = "spm", catalog = "")
@IdClass(SelectedCourseEntityPK.class)
public class SelectedCourseEntity {
    private int studentUserId;
    private int courseCourseId;
    private Timestamp approveTime;
    private BigDecimal avgOnlineScore;
    private BigDecimal midScore;
    private BigDecimal finalScore;
    private BigDecimal totalScore;

    @Id
    @Column(name = "student_user_id")
    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    @Id
    @Column(name = "course_course_id")
    public int getCourseCourseId() {
        return courseCourseId;
    }

    public void setCourseCourseId(int courseCourseId) {
        this.courseCourseId = courseCourseId;
    }

    @Basic
    @Column(name = "approve_time")
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Basic
    @Column(name = "avg_online_score")
    public BigDecimal getAvgOnlineScore() {
        return avgOnlineScore;
    }

    public void setAvgOnlineScore(BigDecimal avgOnlineScore) {
        this.avgOnlineScore = avgOnlineScore;
    }

    @Basic
    @Column(name = "mid_score")
    public BigDecimal getMidScore() {
        return midScore;
    }

    public void setMidScore(BigDecimal midScore) {
        this.midScore = midScore;
    }

    @Basic
    @Column(name = "final_score")
    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    @Basic
    @Column(name = "total_score")
    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectedCourseEntity that = (SelectedCourseEntity) o;
        return studentUserId == that.studentUserId &&
                courseCourseId == that.courseCourseId &&
                Objects.equals(approveTime, that.approveTime) &&
                Objects.equals(avgOnlineScore, that.avgOnlineScore) &&
                Objects.equals(midScore, that.midScore) &&
                Objects.equals(finalScore, that.finalScore) &&
                Objects.equals(totalScore, that.totalScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentUserId, courseCourseId, approveTime, avgOnlineScore, midScore, finalScore, totalScore);
    }
}

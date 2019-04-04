package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "selected_course", schema = "spm")
@IdClass(SelectedCourseEntityPK.class)
public class SelectedCourseEntity {
    private int studentUserId;
    private int courseCourseId;
    private Timestamp timeApproved;
    private BigDecimal avgOnlineScore;
    private BigDecimal midScore;
    private BigDecimal finalScore;
    private BigDecimal totalScore;

    @Id
    @Column(name = "student_user_id", nullable = false)
    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    @Id
    @Column(name = "course_course_id", nullable = false)
    public int getCourseCourseId() {
        return courseCourseId;
    }

    public void setCourseCourseId(int courseCourseId) {
        this.courseCourseId = courseCourseId;
    }

    @Basic
    @Column(name = "time_approved", nullable = false)
    public Timestamp getTimeApproved() {
        return timeApproved;
    }

    public void setTimeApproved(Timestamp timeApproved) {
        this.timeApproved = timeApproved;
    }

    @Basic
    @Column(name = "avg_online_score", nullable = true, precision = 1)
    public BigDecimal getAvgOnlineScore() {
        return avgOnlineScore;
    }

    public void setAvgOnlineScore(BigDecimal avgOnlineScore) {
        this.avgOnlineScore = avgOnlineScore;
    }

    @Basic
    @Column(name = "mid_score", nullable = true, precision = 1)
    public BigDecimal getMidScore() {
        return midScore;
    }

    public void setMidScore(BigDecimal midScore) {
        this.midScore = midScore;
    }

    @Basic
    @Column(name = "final_score", nullable = true, precision = 1)
    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    @Basic
    @Column(name = "total_score", nullable = true, precision = 1)
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
                Objects.equals(timeApproved, that.timeApproved) &&
                Objects.equals(avgOnlineScore, that.avgOnlineScore) &&
                Objects.equals(midScore, that.midScore) &&
                Objects.equals(finalScore, that.finalScore) &&
                Objects.equals(totalScore, that.totalScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentUserId, courseCourseId, timeApproved, avgOnlineScore, midScore, finalScore, totalScore);
    }
}

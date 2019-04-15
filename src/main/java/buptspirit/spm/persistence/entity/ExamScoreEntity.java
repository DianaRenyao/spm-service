package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exam_score", schema = "spm")
@IdClass(ExamScoreEntityPK.class)
public class ExamScoreEntity {
    private int examId;
    private int selectedCourseStudentUserId;
    private int selectedCourseCourseCourseId;
    private Integer examScore;

    @Id
    @Column(name = "exam_id")
    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    @Id
    @Column(name = "selected_course_student_user_id")
    public int getSelectedCourseStudentUserId() {
        return selectedCourseStudentUserId;
    }

    public void setSelectedCourseStudentUserId(int selectedCourseStudentUserId) {
        this.selectedCourseStudentUserId = selectedCourseStudentUserId;
    }

    @Id
    @Column(name = "selected_course_course_course_id")
    public int getSelectedCourseCourseCourseId() {
        return selectedCourseCourseCourseId;
    }

    public void setSelectedCourseCourseCourseId(int selectedCourseCourseCourseId) {
        this.selectedCourseCourseCourseId = selectedCourseCourseCourseId;
    }

    @Basic
    @Column(name = "exam_score")
    public Integer getExamScore() {
        return examScore;
    }

    public void setExamScore(Integer examScore) {
        this.examScore = examScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamScoreEntity that = (ExamScoreEntity) o;
        return examId == that.examId &&
                selectedCourseStudentUserId == that.selectedCourseStudentUserId &&
                selectedCourseCourseCourseId == that.selectedCourseCourseCourseId &&
                Objects.equals(examScore, that.examScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, selectedCourseStudentUserId, selectedCourseCourseCourseId, examScore);
    }
}

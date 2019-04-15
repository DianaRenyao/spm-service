package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ExamScoreEntityPK implements Serializable {
    private int examId;
    private int selectedCourseStudentUserId;
    private int selectedCourseCourseCourseId;

    @Column(name = "exam_id")
    @Id
    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    @Column(name = "selected_course_student_user_id")
    @Id
    public int getSelectedCourseStudentUserId() {
        return selectedCourseStudentUserId;
    }

    public void setSelectedCourseStudentUserId(int selectedCourseStudentUserId) {
        this.selectedCourseStudentUserId = selectedCourseStudentUserId;
    }

    @Column(name = "selected_course_course_course_id")
    @Id
    public int getSelectedCourseCourseCourseId() {
        return selectedCourseCourseCourseId;
    }

    public void setSelectedCourseCourseCourseId(int selectedCourseCourseCourseId) {
        this.selectedCourseCourseCourseId = selectedCourseCourseCourseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamScoreEntityPK that = (ExamScoreEntityPK) o;
        return examId == that.examId &&
                selectedCourseStudentUserId == that.selectedCourseStudentUserId &&
                selectedCourseCourseCourseId == that.selectedCourseCourseCourseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, selectedCourseStudentUserId, selectedCourseCourseCourseId);
    }
}

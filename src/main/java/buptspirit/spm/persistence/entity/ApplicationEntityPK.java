package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ApplicationEntityPK implements Serializable {
    private int studentUserId;
    private int courseCourseId;

    @Column(name = "student_user_id")
    @Id
    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    @Column(name = "course_course_id")
    @Id
    public int getCourseCourseId() {
        return courseCourseId;
    }

    public void setCourseCourseId(int courseCourseId) {
        this.courseCourseId = courseCourseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationEntityPK that = (ApplicationEntityPK) o;
        return studentUserId == that.studentUserId &&
                courseCourseId == that.courseCourseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentUserId, courseCourseId);
    }
}

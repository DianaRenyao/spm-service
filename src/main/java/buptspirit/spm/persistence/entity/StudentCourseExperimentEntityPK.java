package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class StudentCourseExperimentEntityPK implements Serializable {
    private int studentId;
    private int courseId;
    private int experimentId;

    @Column(name = "student_id", nullable = false)
    @Id
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Column(name = "course_id", nullable = false)
    @Id
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Column(name = "experiment_id", nullable = false)
    @Id
    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseExperimentEntityPK that = (StudentCourseExperimentEntityPK) o;
        return studentId == that.studentId &&
                courseId == that.courseId &&
                experimentId == that.experimentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, experimentId);
    }
}

package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class StudentCourseExperimentEntityPK implements Serializable {
    private int studentId;
    private int courseId;
    private int experimentExperimentId;

    @Column(name = "student_id")
    @Id
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Column(name = "course_id")
    @Id
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Column(name = "experiment_experiment_id")
    @Id
    public int getExperimentExperimentId() {
        return experimentExperimentId;
    }

    public void setExperimentExperimentId(int experimentExperimentId) {
        this.experimentExperimentId = experimentExperimentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseExperimentEntityPK that = (StudentCourseExperimentEntityPK) o;
        return studentId == that.studentId &&
                courseId == that.courseId &&
                experimentExperimentId == that.experimentExperimentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, experimentExperimentId);
    }
}

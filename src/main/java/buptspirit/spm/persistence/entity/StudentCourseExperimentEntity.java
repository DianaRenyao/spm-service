package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student_course_experiment", schema = "spm", catalog = "")
@IdClass(StudentCourseExperimentEntityPK.class)
public class StudentCourseExperimentEntity {
    private int studentId;
    private int courseId;
    private int experimentExperimentId;
    private int docFile;
    private int vedioFile;
    private Integer score;

    @Id
    @Column(name = "student_id")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "course_id")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "experiment_experiment_id")
    public int getExperimentExperimentId() {
        return experimentExperimentId;
    }

    public void setExperimentExperimentId(int experimentExperimentId) {
        this.experimentExperimentId = experimentExperimentId;
    }

    @Basic
    @Column(name = "doc_file")
    public int getDocFile() {
        return docFile;
    }

    public void setDocFile(int docFile) {
        this.docFile = docFile;
    }

    @Basic
    @Column(name = "vedio_file")
    public int getVedioFile() {
        return vedioFile;
    }

    public void setVedioFile(int vedioFile) {
        this.vedioFile = vedioFile;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourseExperimentEntity that = (StudentCourseExperimentEntity) o;
        return studentId == that.studentId &&
                courseId == that.courseId &&
                experimentExperimentId == that.experimentExperimentId &&
                docFile == that.docFile &&
                vedioFile == that.vedioFile &&
                Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, experimentExperimentId, docFile, vedioFile, score);
    }
}

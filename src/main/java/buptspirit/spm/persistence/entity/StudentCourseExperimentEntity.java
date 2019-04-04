package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "student_course_experiment", schema = "spm")
@IdClass(StudentCourseExperimentEntityPK.class)
public class StudentCourseExperimentEntity {
    private int studentId;
    private int courseId;
    private int experimentId;
    private int docFile;
    private int videoFile;
    private Integer score;

    @Id
    @Column(name = "student_id", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "course_id", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "experiment_id", nullable = false)
    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    @Basic
    @Column(name = "doc_file", nullable = false)
    public int getDocFile() {
        return docFile;
    }

    public void setDocFile(int docFile) {
        this.docFile = docFile;
    }

    @Basic
    @Column(name = "video_file", nullable = false)
    public int getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(int videoFile) {
        this.videoFile = videoFile;
    }

    @Basic
    @Column(name = "score", nullable = true)
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
                experimentId == that.experimentId &&
                docFile == that.docFile &&
                videoFile == that.videoFile &&
                Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, experimentId, docFile, videoFile, score);
    }
}

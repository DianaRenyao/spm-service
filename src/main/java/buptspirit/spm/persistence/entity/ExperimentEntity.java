package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "experiment", schema = "spm", catalog = "")
public class ExperimentEntity {
    private int experimentId;
    private String experimentName;
    private String description;
    private Date startTime;
    private Date finishTime;
    private int courseId;

    @Id
    @Column(name = "experiment_id")
    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    @Basic
    @Column(name = "experiment_name")
    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "finish_time")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "course_id")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentEntity that = (ExperimentEntity) o;
        return experimentId == that.experimentId &&
                courseId == that.courseId &&
                Objects.equals(experimentName, that.experimentName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(finishTime, that.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentId, experimentName, description, startTime, finishTime, courseId);
    }
}

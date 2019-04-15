package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ExperimentEntity;

import java.sql.Date;

;

public class ExperimentMessage {
    private int experimentId;
    private String experimentName;
    private String description;
    private Date startDate;
    private Date finishDate;
    private int courseId;

    public static ExperimentMessage fromEntity(ExperimentEntity experimentEntity) {
        ExperimentMessage experimentMessage = new ExperimentMessage();
        experimentMessage.setExperimentId(experimentEntity.getExperimentId());
        experimentMessage.setExperimentName(experimentEntity.getExperimentName());
        experimentMessage.setDescription(experimentEntity.getDescription());
        experimentMessage.setStartDate(experimentEntity.getStartDate());
        experimentMessage.setFinishDate(experimentEntity.getFinishDate());
        experimentMessage.setCourseId(experimentEntity.getCourseId());
        return experimentMessage;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}

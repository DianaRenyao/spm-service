package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ExperimentEntity;

import java.sql.Date;

public class ExperimentCreationMessage {
    private String experimentName;
    private String description;
    private Date startDate;
    private Date finishDate;

    public static ExperimentCreationMessage fromEntity(ExperimentEntity experimentEntity) {
        ExperimentCreationMessage experimentMessage = new ExperimentCreationMessage();
        experimentMessage.setExperimentName(experimentEntity.getExperimentName());
        experimentMessage.setDescription(experimentEntity.getDescription());
        experimentMessage.setStartDate(experimentEntity.getStartDate());
        experimentMessage.setFinishDate(experimentEntity.getFinishDate());
        return experimentMessage;
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

}

package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ExperimentFileEntityPK implements Serializable {
    private int experimentExperimentId;
    private int filesourceFilesouceId;

    @Column(name = "experiment_experiment_id")
    @Id
    public int getExperimentExperimentId() {
        return experimentExperimentId;
    }

    public void setExperimentExperimentId(int experimentExperimentId) {
        this.experimentExperimentId = experimentExperimentId;
    }

    @Column(name = "filesource_filesouce_id")
    @Id
    public int getFilesourceFilesouceId() {
        return filesourceFilesouceId;
    }

    public void setFilesourceFilesouceId(int filesourceFilesouceId) {
        this.filesourceFilesouceId = filesourceFilesouceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentFileEntityPK that = (ExperimentFileEntityPK) o;
        return experimentExperimentId == that.experimentExperimentId &&
                filesourceFilesouceId == that.filesourceFilesouceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentExperimentId, filesourceFilesouceId);
    }
}

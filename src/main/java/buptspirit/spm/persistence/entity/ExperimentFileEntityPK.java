package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ExperimentFileEntityPK implements Serializable {
    private int experimentId;
    private int fileSourceId;

    @Column(name = "experiment_id", nullable = false)
    @Id
    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    @Column(name = "file_source_id", nullable = false)
    @Id
    public int getFileSourceId() {
        return fileSourceId;
    }

    public void setFileSourceId(int fileSourceId) {
        this.fileSourceId = fileSourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentFileEntityPK that = (ExperimentFileEntityPK) o;
        return experimentId == that.experimentId &&
                fileSourceId == that.fileSourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentId, fileSourceId);
    }
}

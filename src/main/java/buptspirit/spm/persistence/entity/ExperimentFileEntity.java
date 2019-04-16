package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "experiment_file", schema = "spm")
@IdClass(ExperimentFileEntityPK.class)
public class ExperimentFileEntity {
    private int experimentId;
    private int fileSourceId;

    @Id
    @Column(name = "experiment_id", nullable = false)
    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    @Id
    @Column(name = "file_source_id", nullable = false)
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
        ExperimentFileEntity that = (ExperimentFileEntity) o;
        return experimentId == that.experimentId &&
                fileSourceId == that.fileSourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentId, fileSourceId);
    }
}

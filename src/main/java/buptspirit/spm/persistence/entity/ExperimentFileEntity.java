package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "experiment_file", schema = "spm", catalog = "")
@IdClass(ExperimentFileEntityPK.class)
public class ExperimentFileEntity {
    private int experimentExperimentId;
    private int filesourceFilesouceId;

    @Id
    @Column(name = "experiment_experiment_id")
    public int getExperimentExperimentId() {
        return experimentExperimentId;
    }

    public void setExperimentExperimentId(int experimentExperimentId) {
        this.experimentExperimentId = experimentExperimentId;
    }

    @Id
    @Column(name = "filesource_filesouce_id")
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
        ExperimentFileEntity that = (ExperimentFileEntity) o;
        return experimentExperimentId == that.experimentExperimentId &&
                filesourceFilesouceId == that.filesourceFilesouceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentExperimentId, filesourceFilesouceId);
    }
}

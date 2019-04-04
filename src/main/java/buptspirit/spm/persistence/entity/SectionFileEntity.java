package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "section_file", schema = "spm")
@IdClass(SectionFileEntityPK.class)
public class SectionFileEntity {
    private int sectionId;
    private int fileSourceId;

    @Id
    @Column(name = "section_id", nullable = false)
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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
        SectionFileEntity that = (SectionFileEntity) o;
        return sectionId == that.sectionId &&
                fileSourceId == that.fileSourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId, fileSourceId);
    }
}

package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class SectionFileEntityPK implements Serializable {
    private int sectionId;
    private int fileSourceId;

    @Column(name = "section_id", nullable = false)
    @Id
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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
        SectionFileEntityPK that = (SectionFileEntityPK) o;
        return sectionId == that.sectionId &&
                fileSourceId == that.fileSourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId, fileSourceId);
    }
}

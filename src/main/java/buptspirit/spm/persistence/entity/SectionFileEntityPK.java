package buptspirit.spm.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class SectionFileEntityPK implements Serializable {
    private int sectionSectionId;
    private int filesourceFilesouceId;

    @Column(name = "section_section_id")
    @Id
    public int getSectionSectionId() {
        return sectionSectionId;
    }

    public void setSectionSectionId(int sectionSectionId) {
        this.sectionSectionId = sectionSectionId;
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
        SectionFileEntityPK that = (SectionFileEntityPK) o;
        return sectionSectionId == that.sectionSectionId &&
                filesourceFilesouceId == that.filesourceFilesouceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionSectionId, filesourceFilesouceId);
    }
}

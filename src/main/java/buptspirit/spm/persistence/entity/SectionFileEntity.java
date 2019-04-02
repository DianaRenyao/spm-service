package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "section_file", schema = "spm", catalog = "")
@IdClass(SectionFileEntityPK.class)
public class SectionFileEntity {
    private int sectionSectionId;
    private int filesourceFilesouceId;

    @Id
    @Column(name = "section_section_id")
    public int getSectionSectionId() {
        return sectionSectionId;
    }

    public void setSectionSectionId(int sectionSectionId) {
        this.sectionSectionId = sectionSectionId;
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
        SectionFileEntity that = (SectionFileEntity) o;
        return sectionSectionId == that.sectionSectionId &&
                filesourceFilesouceId == that.filesourceFilesouceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionSectionId, filesourceFilesouceId);
    }
}

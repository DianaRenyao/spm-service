package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "section", schema = "spm")
public class SectionEntity {
    private int sectionId;
    private int chapterId;
    private byte sequence;
    private String sectionName;

    @Id
    @GeneratedValue
    @Column(name = "section_id", nullable = false)
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    @Basic
    @Column(name = "chapter_id", nullable = false)
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    @Basic
    @Column(name = "sequence", nullable = false)
    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }

    @Basic
    @Column(name = "section_name", nullable = false, length = 256)
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionEntity that = (SectionEntity) o;
        return sectionId == that.sectionId &&
                chapterId == that.chapterId &&
                sequence == that.sequence &&
                Objects.equals(sectionName, that.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId, chapterId, sequence, sectionName);
    }
}

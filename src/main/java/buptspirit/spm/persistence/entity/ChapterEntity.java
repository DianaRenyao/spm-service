package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chapter", schema = "spm", catalog = "")
public class ChapterEntity {
    private int chapterId;
    private int courseId;
    private byte sequence;
    private String chapterName;

    @Id
    @Column(name = "chapter_id")
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    @Basic
    @Column(name = "course_id")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "sequence")
    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }

    @Basic
    @Column(name = "chapter_name")
    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChapterEntity that = (ChapterEntity) o;
        return chapterId == that.chapterId &&
                courseId == that.courseId &&
                sequence == that.sequence &&
                Objects.equals(chapterName, that.chapterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterId, courseId, sequence, chapterName);
    }
}

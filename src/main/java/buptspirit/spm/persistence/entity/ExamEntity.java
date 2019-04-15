package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exam", schema = "spm")
public class ExamEntity {
    private int examId;
    private int chapterId;

    @Id
    @Column(name = "exam_id", nullable = false)
    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    @Basic
    @Column(name = "chapter_id", nullable = false)
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamEntity that = (ExamEntity) o;
        return examId == that.examId &&
                chapterId == that.chapterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, chapterId);
    }
}

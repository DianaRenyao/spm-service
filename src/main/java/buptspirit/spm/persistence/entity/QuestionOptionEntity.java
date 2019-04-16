package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "question_option", schema = "spm")
public class QuestionOptionEntity {
    private int questionOptionId;
    private String questionOptionDetail;
    private int questionId;

    @Id
    @GeneratedValue
    @Column(name = "question_option_id", nullable = false)
    public int getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(int questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    @Basic
    @Column(name = "question_option_detail", nullable = false, length = 256)
    public String getQuestionOptionDetail() {
        return questionOptionDetail;
    }

    public void setQuestionOptionDetail(String questionOptionDetail) {
        this.questionOptionDetail = questionOptionDetail;
    }

    @Basic
    @Column(name = "question_id", nullable = false)
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionOptionEntity that = (QuestionOptionEntity) o;
        return questionOptionId == that.questionOptionId &&
                questionId == that.questionId &&
                Objects.equals(questionOptionDetail, that.questionOptionDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionOptionId, questionOptionDetail, questionId);
    }
}

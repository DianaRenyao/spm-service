package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "option", schema = "spm", catalog = "")
public class OptionEntity {
    private int optionId;
    private String optionDetail;
    private int questionId;

    @Id
    @Column(name = "option_id")
    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    @Basic
    @Column(name = "option_detail")
    public String getOptionDetail() {
        return optionDetail;
    }

    public void setOptionDetail(String optionDetail) {
        this.optionDetail = optionDetail;
    }

    @Basic
    @Column(name = "question_id")
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
        OptionEntity that = (OptionEntity) o;
        return optionId == that.optionId &&
                questionId == that.questionId &&
                Objects.equals(optionDetail, that.optionDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionId, optionDetail, questionId);
    }
}

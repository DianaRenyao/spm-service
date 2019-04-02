package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "question", schema = "spm", catalog = "")
public class QuestionEntity {
    private int questionId;
    private int exam;
    private int answer;
    private String detail;

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "exam")
    public int getExam() {
        return exam;
    }

    public void setExam(int exam) {
        this.exam = exam;
    }

    @Basic
    @Column(name = "answer")
    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return questionId == that.questionId &&
                exam == that.exam &&
                answer == that.answer &&
                Objects.equals(detail, that.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, exam, answer, detail);
    }
}

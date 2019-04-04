package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "application", schema = "spm", catalog = "")
@IdClass(ApplicationEntityPK.class)
public class ApplicationEntity {
    private int studentUserId;
    private int courseCourseId;
    private Timestamp createTime;
    private byte state;

    @Id
    @Column(name = "student_user_id")
    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    @Id
    @Column(name = "course_course_id")
    public int getCourseCourseId() {
        return courseCourseId;
    }

    public void setCourseCourseId(int courseCourseId) {
        this.courseCourseId = courseCourseId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "state")
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return studentUserId == that.studentUserId &&
                courseCourseId == that.courseCourseId &&
                state == that.state &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentUserId, courseCourseId, createTime, state);
    }
}

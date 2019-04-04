package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "spm")
public class StudentEntity {
    private int userId;
    private String clazz;
    private String nickname;
    private String college;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "class", nullable = false, length = 45)
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "nickname", nullable = true, length = 128)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "college", nullable = true, length = 128)
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return userId == that.userId &&
                Objects.equals(clazz, that.clazz) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(college, that.college);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, clazz, nickname, college);
    }
}

package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "spm", catalog = "")
public class StudentEntity {
    private int userId;
    private String clazz;
    private String nickname;
    private String college;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "class")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "college")
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

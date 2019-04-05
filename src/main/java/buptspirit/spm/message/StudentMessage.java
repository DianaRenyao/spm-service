package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.StudentEntity;

public class StudentMessage {

    private UserInfoMessage userInfo;
    private String clazz;
    private String nickname;
    private String college;

    public static StudentMessage fromEntity(StudentEntity entity, UserInfoMessage userInfo) {
        StudentMessage message = new StudentMessage();
        message.userInfo = userInfo;
        message.clazz = entity.getClazz();
        message.nickname = entity.getNickname();
        message.college = entity.getCollege();
        return message;
    }

    public UserInfoMessage getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoMessage userInfo) {
        this.userInfo = userInfo;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}

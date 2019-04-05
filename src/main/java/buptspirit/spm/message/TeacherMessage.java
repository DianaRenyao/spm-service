package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.TeacherEntity;

public class TeacherMessage {

    private UserInfoMessage userInfo;

    public static TeacherMessage fromEntity(TeacherEntity entity, UserInfoMessage user) {
        TeacherMessage message = new TeacherMessage();
        message.setUserInfo(user);
        return message;
    }

    public UserInfoMessage getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoMessage userInfo) {
        this.userInfo = userInfo;
    }
}

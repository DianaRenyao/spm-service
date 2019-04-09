package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.TeacherEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import org.javatuples.Pair;

public class TeacherMessage {

    private UserInfoMessage userInfo;
    private String introduction;

    public static TeacherMessage fromEntity(Pair<TeacherEntity, UserInfoEntity> entities) {
        TeacherMessage message = new TeacherMessage();
        message.setIntroduction(entities.getValue0().getIntroduction());
        message.setUserInfo(UserInfoMessage.fromEntity(entities.getValue1()));
        return message;
    }

    public static TeacherMessage fromEntity(TeacherEntity entity, UserInfoMessage user) {
        TeacherMessage message = new TeacherMessage();
        message.setIntroduction(entity.getIntroduction());
        message.setUserInfo(user);
        return message;
    }

    public UserInfoMessage getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoMessage userInfo) {
        this.userInfo = userInfo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

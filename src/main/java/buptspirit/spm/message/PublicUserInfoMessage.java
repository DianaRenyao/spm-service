package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.UserInfoEntity;

public class PublicUserInfoMessage {

    private String username;
    private String realName;

    public static PublicUserInfoMessage fromEntity(UserInfoEntity entity) {
        PublicUserInfoMessage message = new PublicUserInfoMessage();
        message.setUsername(entity.getUsername());
        message.setRealName(entity.getRealName());
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}

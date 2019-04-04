package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.UserInfoEntity;

import java.util.Date;

public class UserInfoMessage {

    private int id;
    private String username;
    private Date timeCreated;
    private String role;
    private String realName;
    private String email;
    private String phone;

    public static UserInfoMessage FromEntity(UserInfoEntity entity) {
        UserInfoMessage message = new UserInfoMessage();
        message.id = entity.getUserId();
        message.username = entity.getUsername();
        message.timeCreated = entity.getTimeCreated();
        message.role = entity.getRole();
        message.realName = entity.getRealName();
        message.email = entity.getEmail();
        message.phone = entity.getPhone();
        return message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

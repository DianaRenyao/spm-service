package buptspirit.spm.message;

import java.util.Date;

public class SessionMessage {

    private boolean authenticated;
    private String token;
    private Date issuedAt;
    private Date expiresAt;
    private UserInfoMessage userInfo;

    public static SessionMessage Unauthenticated() {
        SessionMessage message = new SessionMessage();
        message.setAuthenticated(false);
        return message;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UserInfoMessage getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoMessage userInfo) {
        this.userInfo = userInfo;
    }
}



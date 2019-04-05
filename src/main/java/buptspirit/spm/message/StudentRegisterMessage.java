package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;

public class StudentRegisterMessage {

    public void enforce() throws ServiceException {
        if (username == null || username.isEmpty())
            throw ServiceError.POST_STUDENT_USERNAME_IS_EMPTY.toException();
        if (password == null || password.isEmpty())
            throw ServiceError.POST_STUDENT_PASSWORD_IS_EMPTY.toException();
        if (realName == null || realName.isEmpty())
            throw ServiceError.POST_STUDENT_REAL_NAME_IS_EMPTY.toException();
        if (email == null || email.isEmpty())
            throw ServiceError.POST_STUDENT_EMAIL_IS_EMPTY.toException();
        if (phone == null || phone.isEmpty())
            throw ServiceError.POST_STUDENT_PHONE_IS_EMPTY.toException();
        if (clazz == null || clazz.isEmpty())
            throw ServiceError.POST_STUDENT_CLAZZ_IS_EMPTY.toException();
        if (!username.matches("\\d{10}"))
            throw ServiceError.POST_STUDENT_USERNAME_IS_NOT_AN_ID.toException();
    }

    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String clazz;
    private String nickname;
    private String college;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

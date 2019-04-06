package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class TeacherRegisterMessage implements InputMessage {

    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;

    @SuppressWarnings("Duplicates")
    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(username != null && !username.isEmpty());
        serviceAssert(password != null && !password.isEmpty());
        serviceAssert(realName != null && !realName.isEmpty());
        serviceAssert(email != null && !email.isEmpty());
        serviceAssert(phone != null && !phone.isEmpty());
        serviceAssert(username.matches("^\\d{10}$"));
    }

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

}

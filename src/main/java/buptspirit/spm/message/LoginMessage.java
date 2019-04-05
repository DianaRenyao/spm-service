package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class LoginMessage implements InputMessage {

    private String username;
    private String password;

    public void enforce() throws ServiceAssertionException {
        serviceAssert(username != null && !username.isEmpty());
        serviceAssert(password != null && !password.isEmpty());
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
}

package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;

public class LoginMessage {

    private String username;
    private String password;

    public void enforce() throws ServiceException {
        if (username == null)
            throw ServiceError.LOGIN_USERNAME_IS_EMPTY.toException();
        if (password == null)
            throw ServiceError.LOGIN_PASSWORD_IS_EMPTY.toException();
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

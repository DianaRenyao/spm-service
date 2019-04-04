package buptspirit.spm.logic;

import buptspirit.spm.password.PasswordHash;
import buptspirit.spm.persistence.entity.UserInfo;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import buptspirit.spm.rest.exception.ServiceError;
import buptspirit.spm.rest.exception.ServiceException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

import static buptspirit.spm.persistence.JpaUtility.transactional;

@Singleton
public class UserLogic {

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private PasswordHash passwordHash;

    // return null if failed to verify user
    public UserInfo verify(String username, String password) {
        UserInfo info = transactional(
                em -> userInfoFacade.findByUsername(em, username),
                "failed to find user by username"
        );
        if (info != null && passwordHash.verify(password.toCharArray(), info.getPassword())) {
            return info;
        } else {
            return null;
        }
    }

    // return null if user already exists
    public UserInfo create(String username, String password, String role) throws ServiceException {
        boolean exists = transactional(
                em -> userInfoFacade.findByUsername(em, username) != null,
                "failed to find user by name"
        );
        if (exists)
            throw ServiceError.USERNAME_ALREADY_EXISTS.toException();
        UserInfo newUser = new UserInfo();
        newUser.setUsername(username);
        newUser.setPassword(passwordHash.generate(password.toCharArray()));
        newUser.setRole(role);
        newUser.setDateCreated(new Date());
       /* transactional(
                em -> {
                    userInfoFacade.create(em, newUser);
                    return null;
                },
                "failed to create user"
        );*/
        return newUser;
    }
}

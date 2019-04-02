package buptspirit.spm.logic;

import buptspirit.spm.password.PasswordHash;
import buptspirit.spm.persistence.entity.UserInfo;
import buptspirit.spm.persistence.facade.UserInfoFacade;

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

    public UserInfo create(String username, String password, String role) {
        UserInfo newUser = new UserInfo();
        newUser.setUsername(username);
        newUser.setPassword(passwordHash.generate(password.toCharArray()));
        newUser.setRole(role);
        newUser.setDateCreated(new Date());
        transactional(
                em -> {
                    userInfoFacade.create(em, newUser);
                    return null;
                },
                "failed to create user"
        );
        return newUser;
    }
}

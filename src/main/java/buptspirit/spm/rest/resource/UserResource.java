package buptspirit.spm.rest.resource;

import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.persistence.entity.UserInfo;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.message.RegisterMessage;
import buptspirit.spm.rest.message.UserInfoMessage;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("user")
public class UserResource {

    @Inject
    private Logger logger;

    @Context
    private SecurityContext securityContext;

    @Inject
    private UserLogic userLogic;

    public UserResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfoMessage createUser(RegisterMessage register) {
        String role = register.getRole();
        switch (role) {
            case "student":
                break;
            case "teacher":
                if (!this.securityContext.isUserInRole(Role.Administrator.getName()))
                    throw new ServiceException("teacher user must be created by administrator");
                break;
            case "administrator":
                if (!this.securityContext.isUserInRole(Role.Administrator.getName()))
                    throw new ServiceException("administrator user must be created by administrator");
                break;
            default:
                throw new ServiceException("invalid role");
        }
        String username = register.getUsername();
        String password = register.getPassword();
        if (username.isEmpty())
            throw new ServiceException("username is empty");
        if (password.isEmpty())
            throw new ServiceException("password is empty");
        UserInfo info = userLogic.create(username, password, role);
        UserInfoMessage userInfoMessage = new UserInfoMessage();
        userInfoMessage.setId(info.getId());
        userInfoMessage.setRole(info.getRole());
        userInfoMessage.setUsername(info.getUsername());
        return userInfoMessage;
    }
}

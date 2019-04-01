package buptspirit.spm.rest.resource;

import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.persistence.entity.UserInfo;
import buptspirit.spm.rest.exception.ServiceException;
import buptspirit.spm.rest.message.LoginMessage;
import buptspirit.spm.rest.message.SessionMessage;
import buptspirit.spm.rest.token.TokenManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("session")
public class SessionResource {

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 1 day

    @Context
    private SecurityContext securityContext;

    @Inject
    private TokenManager tokenManager;

    @Inject
    private UserLogic userLogic;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SessionMessage createSession(LoginMessage login) throws ServiceException {
        String username = login.getUsername();
        String password = login.getPassword();
        UserInfo userInfo = userLogic.verify(username, password);
        if (userInfo != null) {
            int id = userInfo.getId();
            String role = userInfo.getRole();
            SessionMessage sessionMessage = new SessionMessage();
            String token = tokenManager.issue(id, username, role, EXPIRE_TIME);
            sessionMessage.setId(id);
            sessionMessage.setRole(role);
            sessionMessage.setUsername(username);
            sessionMessage.setToken(token);
            return sessionMessage;
        } else {
            throw new ServiceException(Response.Status.UNAUTHORIZED, "invalid password or username");
        }
    }
}

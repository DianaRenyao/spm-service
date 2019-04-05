package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.SessionLogic;
import buptspirit.spm.message.LoginMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("session")
public class SessionResource {

    @Context
    private SecurityContext securityContext;

    @Inject
    private SessionLogic sessionLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage remoteSession;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SessionMessage getCurrentSession() {
        SessionMessage copied = new SessionMessage();
        copied.setAuthenticated(remoteSession.isAuthenticated());
        copied.setToken(remoteSession.getToken());
        copied.setIssuedAt(remoteSession.getIssuedAt());
        copied.setExpiresAt(remoteSession.getExpiresAt());
        copied.setUserInfo(remoteSession.getUserInfo());
        return copied;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SessionMessage createSession(LoginMessage login) throws ServiceException {
        return sessionLogic.createSession(login);
    }
}

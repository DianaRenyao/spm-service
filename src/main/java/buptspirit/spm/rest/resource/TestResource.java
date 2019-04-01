package buptspirit.spm.rest.resource;

import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.session.RemoteSession;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// this resource is just a example and test of remote session
@Path("test")
public class TestResource {

    @Inject
    @AuthenticatedSession
    private RemoteSession remoteSession;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RemoteSession returnTestResult() {
        RemoteSession copiedSession = new RemoteSession();
        copiedSession.setAuthenticated(remoteSession.isAuthenticated());
        copiedSession.setUsername(remoteSession.getUsername());
        copiedSession.setRole(remoteSession.getRole());
        copiedSession.setId(remoteSession.getId());
        copiedSession.setIssuedAt(remoteSession.getIssuedAt());
        copiedSession.setExpiresAt(remoteSession.getExpiresAt());
        return copiedSession;
    }
}

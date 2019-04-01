package buptspirit.spm.rest.session;

import buptspirit.spm.rest.filter.AuthenticatedSession;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@RequestScoped
public class RemoteSessionProducer {

    @Produces
    @RequestScoped
    @AuthenticatedSession
    private RemoteSession remoteSession;

    public void handleAuthenticationEvent(@Observes @AuthenticatedSession RemoteSession remoteSession) {
        this.remoteSession = remoteSession;
    }
}

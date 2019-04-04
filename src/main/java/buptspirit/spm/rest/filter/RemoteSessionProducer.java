package buptspirit.spm.rest.filter;

import buptspirit.spm.message.SessionMessage;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@RequestScoped
public class RemoteSessionProducer {

    @Produces
    @RequestScoped
    @AuthenticatedSession
    private SessionMessage remoteSession;

    public void handleAuthenticationEvent(@Observes @AuthenticatedSession SessionMessage remoteSession) {
        this.remoteSession = remoteSession;
    }
}

package buptspirit.spm.rest.resource;

import buptspirit.spm.logic.ApplicationLogic;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("applications")
public class ApplicationResource {

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @Inject
    private ApplicationLogic applicationLogic;

    @POST
    @Secured({Role.Student})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationCreationMessage createApplication(ApplicationCreationMessage applicationCreationMessage){
        return applicationLogic.createApplication(applicationCreationMessage,sessionMessage);
    }
}

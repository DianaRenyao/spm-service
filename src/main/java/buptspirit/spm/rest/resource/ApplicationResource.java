package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ApplicationLogic;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.ApplicationMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public ApplicationMessage createApplication(ApplicationCreationMessage applicationCreationMessage) throws ServiceAssertionException, ServiceException {
        return applicationLogic.createApplication(applicationCreationMessage,sessionMessage);
    }

    @PUT
    @Secured({Role.Teacher, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationMessage modifyApplication(
            @QueryParam("isPass") boolean isPass,
            @QueryParam("courseId") int courseId,
            @QueryParam("studentUserId") int studentUserId) throws ServiceException {
        if (isPass)
            return applicationLogic.passApplication(courseId,studentUserId,sessionMessage);
        else
            return applicationLogic.rejectApplication(courseId,studentUserId,sessionMessage);
    }

    @GET
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationMessage> getWantedApplications(
            @QueryParam("courseId") int courseId,
            @QueryParam("studentUserId") int studentUserId
    ){
     return applicationLogic.getWantedApplications(courseId,sessionMessage);
    }

}

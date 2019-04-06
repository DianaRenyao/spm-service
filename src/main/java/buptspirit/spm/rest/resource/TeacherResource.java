package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.message.StudentMessage;
import buptspirit.spm.message.TeacherMessage;
import buptspirit.spm.message.TeacherRegisterMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("teachers")
public class TeacherResource {


    @Inject
    private UserLogic userLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Path("{username}")
    @Secured({Role.Teacher, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherMessage getTeacher(
            @PathParam("username") String username
    ) throws ServiceException, ServiceAssertionException {
        if (sessionMessage.getUserInfo().getRole().equals("teacher")) {
            if (!sessionMessage.getUserInfo().getUsername().equals(username)) {
                throw ServiceError.FORBIDDEN.toException();
            }
        }
        return userLogic.getTeacher(username);
    }

    @POST
    @Secured({Role.Administrator})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherMessage register(TeacherRegisterMessage registerMessage) throws ServiceException, ServiceAssertionException {
        return userLogic.createTeacher(registerMessage);
    }
}

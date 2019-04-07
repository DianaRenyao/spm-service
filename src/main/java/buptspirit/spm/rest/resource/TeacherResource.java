package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.message.TeacherMessage;
import buptspirit.spm.message.TeacherRegisterMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
        if (sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName())) {
            if (!sessionMessage.getUserInfo().getUsername().equals(username)) {
                throw ServiceError.FORBIDDEN.toException();
            }
        }
        return userLogic.getTeacher(username);
    }

    @GET
    @Secured({Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeacherMessage> getAllTeachers() throws ServiceException, ServiceAssertionException {
        return userLogic.getAllTeachers();
    }

    @POST
    @Secured({Role.Administrator})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherMessage register(TeacherRegisterMessage registerMessage) throws ServiceException, ServiceAssertionException {
        return userLogic.createTeacher(registerMessage);
    }
}
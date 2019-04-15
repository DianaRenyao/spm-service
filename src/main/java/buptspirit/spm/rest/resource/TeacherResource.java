package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.NoticeLogic;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.NoticeMessage;
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
    private NoticeLogic noticeLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherMessage getTeacher(
            @PathParam("username") String username
    ) throws ServiceException, ServiceAssertionException {
//        if (sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName())) {
//            if (!sessionMessage.getUserInfo().getUsername().equals(username)) {
//                throw ServiceError.FORBIDDEN.toException();
//            }
//        }
        // all user can get teacher
        return userLogic.getTeacher(username);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeacherMessage> getAllTeachers() throws ServiceException {
        return userLogic.getAllTeachers();
    }

    @POST
    @Secured({Role.Administrator})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TeacherMessage register(TeacherRegisterMessage registerMessage) throws ServiceException, ServiceAssertionException {
        return userLogic.createTeacher(registerMessage);
    }


    @GET
    @Path("/{username}/notices")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoticeMessage> getTeacherNotice(
            @PathParam("username") String username
    ) throws ServiceException {
        if (!username.equals(sessionMessage.getUserInfo().getUsername())) {
            throw ServiceError.FORBIDDEN.toException();
        }
        return noticeLogic.getTeacherNotices(username, sessionMessage);
    }
}

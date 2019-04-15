package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.NoticeLogic;
import buptspirit.spm.logic.SelectedCourseLogic;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.*;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
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

    @Inject
    SelectedCourseLogic selectedCourseLogic;

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

    @GET
    @Path("{username}/courses/{courseId}/students")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelectedCourseMessage> getTeacherSelectedCourses(
            @PathParam("username") String username,
            @PathParam("courseId") int courseId
    ) throws ServiceException {
        return selectedCourseLogic.getTeacherSelectedCourses(courseId, username, sessionMessage);
    }

    @POST
    @Path("{username}/courses/{courseId}/students")
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SelectedCourseMessage createSelectedCourse(
            SelectedCourseCreationMessage selectedCourseCreationMessage,
            @QueryParam("studentUserId") int studentUserId,
            @PathParam("courseId") int courseId,
            @PathParam("username") String username
    ) throws ServiceException {
        return selectedCourseLogic.editSelectedCourse(selectedCourseCreationMessage, studentUserId, courseId, sessionMessage, username);
    }
}

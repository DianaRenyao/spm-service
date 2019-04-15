package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.SelectedCourseCreationMessage;
import buptspirit.spm.message.SelectedCourseMessage;
import buptspirit.spm.logic.SelectedCourseLogic;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path("selectedCourse")
public class SelectedCourseResource {
    @Inject
    SelectedCourseLogic selectedCourseLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Path("course/{courseId}")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelectedCourseMessage> getTeacherSelectedCourses(
            @PathParam("courseId") int courseId
    ) throws ServiceException {
        return selectedCourseLogic.getTeacherSelectedCourses(courseId);
    }

    @GET
    @Secured({Role.Student})
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelectedCourseMessage> getStudentSelectedCourses(
            @PathParam("username") String username
    ) throws ServiceException {
        if (sessionMessage.getUserInfo().getRole().equals(Role.Student.getName())) {
            return selectedCourseLogic.getStudentSelectedCourses(username);
        }
        else return null;
    }

    @POST
    @Path("{score}")
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createSelectedCourse(
            SelectedCourseCreationMessage selectedCourseCreationMessage,
            @QueryParam("studentUserId") String studentUseId,
            @QueryParam("courseCourseId") String courseCourseId
    ) throws ServiceException, ServiceAssertionException {

        selectedCourseLogic.createSelectedCourse(selectedCourseCreationMessage,Integer.parseInt(studentUseId),
                Integer.parseInt(courseCourseId));
        return selectedCourseLogic.addTotalScore(selectedCourseCreationMessage,Integer.parseInt(studentUseId),Integer.parseInt(courseCourseId));
    }
}


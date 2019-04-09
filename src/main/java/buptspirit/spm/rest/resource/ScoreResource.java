package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.ScoreCreateMessage;
import buptspirit.spm.message.SelectedCourseMessage;
import buptspirit.spm.logic.SelectedCourseLogic;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("scores")
public class ScoreResource {
    @Inject
    SelectedCourseLogic selectedCourseLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Path("course/{courseId}")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelectedCourseMessage> getCourseScore(
            @PathParam("courseId") int courseId
    ) throws ServiceException {
        return selectedCourseLogic.getCourseScores(courseId);
    }

    @GET
    @Secured({Role.Student})
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelectedCourseMessage> getScores(
            @PathParam("username") String username
    ) throws ServiceException {
        if (sessionMessage.getUserInfo().getRole().equals(Role.Student.getName())) {
            return selectedCourseLogic.getStudentScores(username);
        }
        else return null;
    }

    @POST
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SelectedCourseMessage createScore(
            ScoreCreateMessage scoreCreateMessage,
            @QueryParam("studentUserId") int studentUseId,
            @QueryParam("courseCourseId") int courseCourseId
    ) throws ServiceException, ServiceAssertionException {
        return selectedCourseLogic.createScore(scoreCreateMessage, studentUseId, courseCourseId);
    }
}


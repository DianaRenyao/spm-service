package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.NoticeMessage;
import buptspirit.spm.message.ScoreCreateMessage;
import buptspirit.spm.message.ScoreMessage;
import buptspirit.spm.logic.ScoreLogic;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("scores")
public class ScoreResource {
    @Inject
    ScoreLogic scoreLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Secured({Role.Teacher,Role.Student})
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScoreMessage> getScores(
            @QueryParam("courseCourseId") int courseCourseId,
            @PathParam("username") String username
    ) throws ServiceException {
        if(sessionMessage.getUserInfo().getRole().equals(Role.Student.getName())){
            return scoreLogic.getStudentScores(username);
        }
        else{
            return scoreLogic.getCourseScores(courseCourseId);
        }
    }

    @POST
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ScoreMessage createScore(
            ScoreCreateMessage scoreCreateMessage,
            @QueryParam("studentUserId") int studentUseId,
            @QueryParam("courseCourseId") int courseCourseId
    ) throws ServiceException, ServiceAssertionException {
        return scoreLogic.createScore(scoreCreateMessage,studentUseId,courseCourseId);
    }
}


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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

    // TODO
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScoreMessage> getScore(
            @PathParam("id") int id
    ) throws ServiceException {
        return scoreLogic.getScore(id);
    }

    @GET
    // @Secured({Role.Student})
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScoreMessage> getScores() {
        return scoreLogic.getAllScores();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ScoreMessage createScore(ScoreCreateMessage scoreCreateMessage) throws ServiceException, ServiceAssertionException {
        return scoreLogic.createScore(scoreCreateMessage);
    }
}


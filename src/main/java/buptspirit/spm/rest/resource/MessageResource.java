package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.logic.MessageLogic;
import buptspirit.spm.message.MessageCreationMessage;
import buptspirit.spm.message.MessageMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

@Path("messages")
public class MessageResource {

    @Inject
    private MessageLogic messageLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMessages(
            @DefaultValue("false") @QueryParam("withReplied") boolean withReplied,
            @QueryParam("first") Integer first,
            @QueryParam("number") Integer number
    ) throws ServiceAssertionException {
        if (first != null && number != null) {
            serviceAssert(first >= 0);
            serviceAssert(number > 0);
        }
        if (withReplied) {
            if (first != null && number != null) {
                return Response.ok().entity(messageLogic.getAllMessageWithRepliedRanged(first, number)).build();
            } else {
                return Response.ok().entity(messageLogic.getAllMessageWithReplied()).build();
            }
        } else {
            if (first != null && number != null) {
                return Response.ok().entity(messageLogic.getAllMessageRanged(first, number)).build();
            } else {
                return Response.ok().entity(messageLogic.getAllMessage()).build();
            }
        }
    }

    @POST
    @Secured({Role.Student, Role.Teacher, Role.Administrator})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MessageMessage createMessage(MessageCreationMessage creationMessage) throws ServiceAssertionException {
        return messageLogic.createMessage(sessionMessage, creationMessage);
    }
}

package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.NoticeLogic;
import buptspirit.spm.message.NoticeCreationMessage;
import buptspirit.spm.message.NoticeMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

@Path("notices")
public class NoticeResource {

    @Inject
    NoticeLogic noticeLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoticeMessage> getNotices(
            @QueryParam("first") Integer first,
            @QueryParam("number") Integer number
    ) throws ServiceAssertionException {
        if (first != null && number != null) {
            serviceAssert(first >= 0);
            serviceAssert(number > 0);
            return noticeLogic.getAllNoticeRanged(first, number);
        } else {
            return noticeLogic.getAllNotice();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NoticeMessage getNotice(
            @PathParam("id") int id
    ) throws ServiceException {
        return noticeLogic.getNotice(id);
    }

    @POST
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNotices(NoticeCreationMessage noticeCreationMessage) throws ServiceAssertionException {
        NoticeMessage message = noticeLogic.createNotice(sessionMessage, noticeCreationMessage);
        URI createdUri = UriBuilder.fromResource(NoticeResource.class)
                .path(Integer.toString(message.getNoticeId()))
                .build();
        return Response.created(createdUri).entity(message).build();
    }

    @PUT
    @Path("{id}")
    @Secured({Role.Teacher, Role.Administrator})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public NoticeMessage modifyNotice(
            @PathParam("id") int id,
            NoticeCreationMessage noticeCreationMessage
    ) throws ServiceException, ServiceAssertionException {
        return noticeLogic.modifyNotice(id, sessionMessage, noticeCreationMessage);
    }

    @DELETE
    @Path("{id}")
    @Secured({Role.Teacher, Role.Administrator})
    public Response deleteNotice(@PathParam("id") int id) throws ServiceException {
        noticeLogic.deleteNotice(id, sessionMessage);
        return Response.noContent().build();
    }
}

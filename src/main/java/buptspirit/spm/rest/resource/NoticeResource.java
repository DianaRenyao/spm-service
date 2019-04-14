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
import javax.ws.rs.*;
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
    public Response getNotices(
            @QueryParam("first") Integer first,
            @QueryParam("number") Integer number
    ) throws ServiceAssertionException {
        List<NoticeMessage> results;
        if (first != null && number != null) {
            serviceAssert(first >= 0);
            serviceAssert(number > 0);
            results = noticeLogic.getAllNoticeRanged(first, number);
        } else {
            results = noticeLogic.getAllNotice();
        }
        return Response.ok(results).header("X-Total-Count", Integer.toString(results.size())).build();
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

    @GET
    @Path("teacherNotices")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoticeMessage> getTeacherNotice() throws ServiceException {
        return noticeLogic.getTeacherNotices(sessionMessage);
    }
}

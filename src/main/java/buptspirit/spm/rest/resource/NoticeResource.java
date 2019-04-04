package buptspirit.spm.rest.resource;

import buptspirit.spm.logic.NoticeLogic;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("notices")
public class NoticeResource {

    @Inject
    NoticeLogic noticeLogic;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoticeLogic.NoticeMsg> getNotices() {
        return noticeLogic.getAllNotice();
    }
}

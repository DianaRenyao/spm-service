package buptspirit.spm.rest.resource;

import buptspirit.spm.logic.ExamLogic;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("exams")
public class ExamResource {
    @Inject
    ExamLogic examLogic;

    @GET
    @Path("id")
    @Secured({Role.Teacher,Role.Student,Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public ExamResource getExam(){
        return null;
    }

}

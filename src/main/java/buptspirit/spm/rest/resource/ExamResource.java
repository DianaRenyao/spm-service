package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ExamLogic;
import buptspirit.spm.message.ExamAnswerMessage;
import buptspirit.spm.message.ExamMessage;
import buptspirit.spm.message.ExamScoreMessage;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("exams")
public class ExamResource {
    @Inject
    ExamLogic examLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @Inject
    private ExamAnswerMessage examAnswerMessage;

    @GET
    @Path("{id}")
    @Secured({Role.Teacher, Role.Student, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public ExamMessage getExam(
            @PathParam("id") int examId,
            @DefaultValue("false") @QueryParam("withAnswer") boolean withAnswer
    ) throws ServiceException {
        if (withAnswer && sessionMessage.getUserInfo().getRole().equals(Role.Student.getName()))
            throw ServiceError.FORBIDDEN.toException();
        return examLogic.getExam(examId, sessionMessage);
    }

    @POST
    @Path("{id}/studentAnswers")
    @Secured({Role.Student})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ExamScoreMessage verifyAnswers(ExamAnswerMessage examAnswerMessage,
                                          @PathParam("id") int id)
            throws ServiceException {
        return examLogic.verifyAnswers(id, examAnswerMessage, sessionMessage);
    }

}

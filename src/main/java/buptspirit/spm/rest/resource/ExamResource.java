package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ExamLogic;
import buptspirit.spm.message.ExamAnswerMessage;
import buptspirit.spm.message.ExamScoreMessage;
import buptspirit.spm.message.StudentExamSummaryMessage;
import buptspirit.spm.message.TeacherExamSummaryMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    @Path("id")
    @Secured({Role.Teacher, Role.Student, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public ExamResource getExam() {
        return null;
    }

    @POST
    @Path("verify")
    @Secured({Role.Student})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ExamScoreMessage verifyAnswers(
            ExamAnswerMessage examAnswerMessage)
    throws ServiceException{
        return examLogic.verifyAnswers(examAnswerMessage,sessionMessage);
    }

    @GET
    @Path("teacher/courses/{courseId}/")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeacherExamSummaryMessage> getTeacherExamSummaries(
            @PathParam("courseId") int courseId
    ) throws ServiceException {
        return examLogic.getTeacherExamSummaries(courseId, sessionMessage);
    }

//    @GET
//    @Path("student/courses/{courseId}/")
//    @Secured({Role.Student})
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<StudentExamSummaryMessage> getStudentExamSummaries(
//            @PathParam("courseId") int courseId
//    ){
//        return examLogic.getStudentExamSummaries(courseId,sessionMessage);
//    }

}

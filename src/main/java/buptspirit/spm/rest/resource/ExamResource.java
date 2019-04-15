package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ExamLogic;
<<<<<<< HEAD
import buptspirit.spm.message.ExamAnswerMessage;
=======
import buptspirit.spm.message.StudentExamSummaryMessage;
import buptspirit.spm.message.TeacherExamSummaryMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
>>>>>>> 8f80bd7db5651d4843ff912272ce054081be2fb3
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("exams")
public class ExamResource {
    @Inject
    ExamLogic examLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Path("id")
    @Secured({Role.Teacher, Role.Student, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public ExamResource getExam() {
        return null;
    }

    @GET
    @Path("answers")
    @Secured({Role.Student})
    public ExamScoreMessage verifyAnswers(ExamAnswerMessage examAnswerMessage){

    }


    @Path("teacher/courses/{courseId}/")
    @Secured({Role.Teacher})
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeacherExamSummaryMessage> getTeacherExamSummaries(
            @PathParam("courseId") int courseId
    ) throws ServiceException {
        return examLogic.getTeacherExamSummaries(courseId, sessionMessage);
    }

    @GET
    @Path("student/courses/{courseId}/")
    @Secured({Role.Student})
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentExamSummaryMessage> getStudentExamSummaries(
            @PathParam("courseId") int courseId
    ){
        return examLogic.getStudentExamSummaries(courseId,sessionMessage);
    }

}

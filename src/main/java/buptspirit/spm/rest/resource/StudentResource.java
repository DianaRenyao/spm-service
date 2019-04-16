package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ApplicationLogic;
import buptspirit.spm.logic.ExamLogic;
import buptspirit.spm.logic.SelectedCourseLogic;
import buptspirit.spm.logic.UserLogic;
import buptspirit.spm.message.*;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("students")
public class StudentResource {
    @Inject
    ExamLogic examLogic;

    @Inject
    private UserLogic userLogic;

    @Inject
    private ApplicationLogic applicationLogic;

    @Inject
    SelectedCourseLogic selectedCourseLogic;

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @GET
    @Path("{username}")
    @Secured({Role.Student, Role.Teacher, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public StudentMessage getStudent(
            @PathParam("username") String username
    ) throws ServiceException, ServiceAssertionException {
        if (sessionMessage.getUserInfo().getRole().equals(Role.Student.getName())) {
            if (!sessionMessage.getUserInfo().getUsername().equals(username)) {
                throw ServiceError.FORBIDDEN.toException();
            }
        }
        return userLogic.getStudent(username);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public StudentMessage register(StudentRegisterMessage registerMessage) throws ServiceException, ServiceAssertionException {
        return userLogic.createStudent(registerMessage);
    }

    // if courseId provided, return single application
    // if courseId not provided, return list of application
    @GET
    @Secured({Role.Student})
    @Path("{username}/applications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentApplication(
            @PathParam("username") String username,
            @QueryParam("courseId") Integer courseId
    ) throws ServiceException {
        if (sessionMessage.getUserInfo().getRole().equals(Role.Student.getName())) {
            if (!sessionMessage.getUserInfo().getUsername().equals(username)) {
                throw ServiceError.FORBIDDEN.toException();
            }
        }
        if (courseId == null) {
            return Response.ok(applicationLogic.getStudentApplication(sessionMessage)).build();
        } else {
            return Response.ok(applicationLogic.getStudentCourseApplication(
                    sessionMessage.getUserInfo().getId(), courseId)).build();
        }
    }

    @GET
    @Secured({Role.Student})
    @Path("{username}/selectedCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SelectedCourseMessage> getStudentSelectedCourses(
            @PathParam("username") String username
    ) throws ServiceException {
        return selectedCourseLogic.getStudentSelectedCourses(username, sessionMessage);
    }

    @GET
    @Path("{username}/courses/{courseId}/exams")
    @Secured({Role.Student})
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentExamSummaryMessage> getStudentExamSummaries(
            @PathParam("courseId") int courseId,
            @PathParam("username") String username
    ) throws ServiceException {
        return examLogic.getStudentExamSummaries(courseId, username, sessionMessage);
    }

}

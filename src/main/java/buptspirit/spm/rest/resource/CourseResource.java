package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ApplicationLogic;
import buptspirit.spm.logic.CourseLogic;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.ApplicationMessage;
import buptspirit.spm.message.CourseCreationMessage;
import buptspirit.spm.message.CourseMessage;
import buptspirit.spm.message.CourseSummaryMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("courses")
public class CourseResource {

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @Inject
    private CourseLogic courseLogic;

    @Inject
    private ApplicationLogic applicationLogic;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseSummaryMessage> getAllCourses(
            @DefaultValue("false") @QueryParam("selectable") boolean selectableOnly,
            @QueryParam("teacher") String teacherUsername
    ) {
        if (selectableOnly) {
            if (teacherUsername == null) {
                return courseLogic.getSelectableCourses();
            } else {
                return courseLogic.getTeacherSelectableCourses(teacherUsername);
            }
        } else {
            if (teacherUsername == null) {
                return courseLogic.getAllCourses();
            } else {
                return courseLogic.getTeacherCourses(teacherUsername);
            }
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseMessage getWantedCourse(@PathParam("id") int id) throws ServiceException {
        return courseLogic.getCourse(id);
    }

    @POST
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CourseMessage createCourse(CourseCreationMessage courseCreationMessage) throws ServiceAssertionException {
        return courseLogic.createCourse(sessionMessage, courseCreationMessage);
    }

    @GET
    @Secured({Role.Teacher, Role.Administrator})
    @Path("{id}/applications")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationMessage> getApplication(@PathParam("id") int courseId) throws ServiceException {
        return applicationLogic.getCourseApplication(courseId, sessionMessage);
    }

    @POST
    @Secured({Role.Student})
    @Path("{id}/applications")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationMessage createApplication(
            ApplicationCreationMessage creationMessage,
            @PathParam("id") int courseId) throws ServiceException {
        return applicationLogic.createApplication(sessionMessage, courseId, creationMessage);
    }

    @PUT
    @Path("{id}/applications")
    @Secured({Role.Teacher, Role.Administrator})
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationMessage modifyApplication(
            @PathParam("id") int courseId,
            @DefaultValue("false") @QueryParam("isPass") boolean isPass,
            @QueryParam("studentUserId") int studentUserId) throws ServiceException {
        if (isPass)
            return applicationLogic.passApplication(courseId, studentUserId, sessionMessage);
        else
            return applicationLogic.rejectApplication(courseId, studentUserId, sessionMessage);
    }


}

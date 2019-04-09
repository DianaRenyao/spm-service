package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.ApplicationLogic;
import buptspirit.spm.logic.ChapterLogic;
import buptspirit.spm.logic.CourseLogic;
import buptspirit.spm.message.*;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @Inject
    private ChapterLogic chapterLogic;

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
    @Secured({Role.Student, Role.Teacher, Role.Administrator})
    @Path("{id}/applications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplication(
            @PathParam("id") int courseId,
            @QueryParam("student") String studentUsername) throws ServiceException {
        if (studentUsername != null) {
            if (sessionMessage.getUserInfo().getRole().equals(Role.Student.getName()) &&
                    !sessionMessage.getUserInfo().getUsername().equals(studentUsername)) {
                throw ServiceError.FORBIDDEN.toException();
            }
            return Response.ok(applicationLogic.getStudentCourseApplication(studentUsername, courseId)).build();
        } else {
            if (sessionMessage.getUserInfo().getRole().equals(Role.Student.getName())) {
                throw ServiceError.FORBIDDEN.toException();
            }
            return Response.ok(applicationLogic.getCourseApplication(courseId, sessionMessage)).build();
        }
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

    @POST
    @Path("{id}/chapters")
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ChapterMessage insertChapter(
            @PathParam("id") int courseId,
            ChapterCreationMessage chapterCreationMessage
    ) throws ServiceAssertionException, ServiceException {
        return chapterLogic.insertChapter(courseId, chapterCreationMessage, sessionMessage);
    }

    @GET
    @Path("{id}/chapters")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChapterMessage> getCourseChapters(
            @PathParam("id") int courseId) {
        return chapterLogic.getCourseChapters(courseId);

    }

    @POST
    @Path("{courseId}/chapters/{chapterId}/sections")
    @Produces(MediaType.APPLICATION_JSON)
    public SectionMessage insertSection(
            @PathParam("courseId") int courseId,
            @PathParam("chapterId") int chapterId,
            SectionCreationMessage sectionCreationMessage
    ) {
        return
    }
}

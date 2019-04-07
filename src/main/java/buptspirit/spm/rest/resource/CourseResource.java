package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.logic.CourseLogic;
import buptspirit.spm.message.CourseCreationMessage;
import buptspirit.spm.message.CourseMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.rest.filter.AuthenticatedSession;
import buptspirit.spm.rest.filter.Role;
import buptspirit.spm.rest.filter.Secured;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("course")
public class CourseResource {

    @Inject
    @AuthenticatedSession
    private SessionMessage sessionMessage;

    @Inject
    private CourseLogic courseLogic;


    @Inject
    private Logger logger;

    @POST
    @Secured({Role.Teacher})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CourseCreationMessage createCourse(CourseCreationMessage courseCreationMessage) throws ServiceAssertionException, ServiceException {
        logger.trace(courseCreationMessage.getStartDate());
        logger.trace(courseCreationMessage.getFinishDate());
        if (sessionMessage.getUserInfo().getRole().equals(Role.Teacher.getName())) {
            if (!sessionMessage.getUserInfo().getUsername().equals(courseCreationMessage.getTeacherUsername())) {
                throw ServiceError.FORBIDDEN.toException();
            }
        }
        return courseLogic.createCourse(courseCreationMessage);
    }

    @GET
    @Secured({Role.Administrator,Role.Teacher,Role.Student})
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseMessage> getAllCourses() throws ServiceException {
        return courseLogic.getAllCourses();
    }


    @GET
    @Path("{condition}")
    @Secured({Role.Administrator,Role.Teacher,Role.Student})
    @Produces(MediaType.APPLICATION_JSON)
    public  List<CourseMessage> getWantedCourses(
            @PathParam("condition") String condition
    ) throws ServiceException, ServiceAssertionException {
        if (condition.equals("optionalCourses"))
            return courseLogic.getOptionalCourses();
        else {
            return courseLogic.getTeacherCourses(condition);
        }
    }
}

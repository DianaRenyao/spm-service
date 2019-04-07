package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.ApplicationMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import buptspirit.spm.persistence.facade.ApplicationFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SelectedCourseFacade;
import buptspirit.spm.rest.filter.ApplicationState;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ApplicationLogic {
    @Inject
    ApplicationFacade applicationFacade;

    @Inject
    CourseFacade courseFacade;

    @Inject
    SelectedCourseFacade selectedCourseFacade;

    @Inject
    private MessageMapper messageMapper;

    @Inject
    private Logger logger;

    public ApplicationMessage createApplication(ApplicationCreationMessage applicationCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException, ServiceException {
        applicationCreationMessage.enforce();
        boolean exists = transactional(
                em -> applicationFacade.findByCourseIdAndStudentId(em,
                        applicationCreationMessage.getCourseId(),
                        sessionMessage.getUserInfo().getId()) != null,
                "failed to find user by name"
        );
        if (exists)
            throw ServiceError.POST_APPLICATION_ALREADY_EXISTS.toException();
        ApplicationEntity newApplication = new ApplicationEntity();
        newApplication.setComment(applicationCreationMessage.getComment());
        newApplication.setState(ApplicationState.Waiting.getState());
        newApplication.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        return transactional(
                em -> {
                    newApplication.setCourseId(applicationCreationMessage.getCourseId());
                    newApplication.setStudentUserId(sessionMessage.getUserInfo().getId());
                    logger.debug("ready to create");
                    applicationFacade.create(em, newApplication);
                    return messageMapper.intoApplicationMessage(em, newApplication);
                },
                "failed to create course"
        );
    }

    public ApplicationMessage passApplication(int courseId, int studentUserId, SessionMessage sessionMessage) throws ServiceException {
        ApplicationEntity thisApplication = transactional(
                em -> applicationFacade.findByCourseIdAndStudentId(em, courseId, studentUserId),
                "failed to find application"
        );
        if (thisApplication == null)
            throw ServiceError.PUT_APPLICATION_NO_SUCH_APPLICATION.toException();
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        thisApplication.setState(ApplicationState.Pass.getState());
        boolean exists = transactional(
                em -> selectedCourseFacade.findByCourseIdAndStudentId(em,
                        courseId, studentUserId) != null,
                "failed to find user by name"
        );
        if (exists)
            throw ServiceError.POST_APPLICATION_ALREADY_EXISTS.toException();
        SelectedCourseEntity newSelected = new SelectedCourseEntity();
        newSelected.setCourseCourseId(courseId);
        newSelected.setStudentUserId(studentUserId);
        newSelected.setTimeApproved(new Timestamp(System.currentTimeMillis()));
        return transactional(
                em -> {
                    selectedCourseFacade.create(em, newSelected);
                    applicationFacade.edit(em, thisApplication);
                    return messageMapper.intoApplicationMessage(em, thisApplication);
                },
                "failed to edit application"
        );
    }

    public ApplicationMessage rejectApplication(int courseId, int studentUserId, SessionMessage sessionMessage) throws ServiceException {
        ApplicationEntity thisApplication = transactional(
                em -> applicationFacade.findByCourseIdAndStudentId(em, courseId, studentUserId),
                "failed to find application"
        );
        if (thisApplication == null)
            throw ServiceError.PUT_APPLICATION_NO_SUCH_APPLICATION.toException();
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        thisApplication.setState(ApplicationState.Reject.getState());
        return transactional(
                em -> {
                    applicationFacade.edit(em, thisApplication);
                    return messageMapper.intoApplicationMessage(em, thisApplication);
                },
                "failed to edit application"
        );
    }

    public List<ApplicationMessage> getWantedApplications(int courseId, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        return transactional(
                em -> applicationFacade.findByCourseId(em, courseId).stream()
                        .map(application -> messageMapper.intoApplicationMessage(em, application))
                        .collect(Collectors.toList()),
                "failed to find teacher"
        );
    }
}


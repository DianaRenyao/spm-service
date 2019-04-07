package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.ApplicationMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.ApplicationEntityPK;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.facade.ApplicationFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.rest.filter.ApplicationState;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ApplicationLogic {

    @Inject
    private ApplicationFacade applicationFacade;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private MessageMapper messageMapper;

    public ApplicationMessage createApplication(ApplicationCreationMessage applicationCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException, ServiceException {
        applicationCreationMessage.enforce();
        CourseEntity course = transactional(
                em -> courseFacade.find(em, applicationCreationMessage.getCourseId()),
                "failed to create course"
        );
        if (course == null)
            throw ServiceError.POST_APPLICATION_NO_SUCH_COURSE.toException();
        if (course.getFinishDate().before(new Date()))
            throw ServiceError.POST_APPLICATION_COURSE_CAN_NOT_BE_APPLIED.toException();

        ApplicationEntityPK pk = new ApplicationEntityPK();
        pk.setCourseId(applicationCreationMessage.getCourseId());
        pk.setStudentUserId(sessionMessage.getUserInfo().getId());
        boolean alreadyApplied = transactional(
                em -> applicationFacade.find(em, pk) != null,
                "failed to find application"
        );
        if (alreadyApplied)
            throw ServiceError.POST_APPLICATION_ALREADY_APPLIED.toException();

        ApplicationEntity newApplication = new ApplicationEntity();
        newApplication.setComment(applicationCreationMessage.getComment());
        newApplication.setState(ApplicationState.Waiting.getState());
        newApplication.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        newApplication.setCourseId(applicationCreationMessage.getCourseId());
        newApplication.setStudentUserId(sessionMessage.getUserInfo().getId());
        return transactional(
                em -> {
                    applicationFacade.create(em, newApplication);
                    return messageMapper.intoApplicationMessage(em, newApplication);
                },
                "failed to create application"
        );
    }
}

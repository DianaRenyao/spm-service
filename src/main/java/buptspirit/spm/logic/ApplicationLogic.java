package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.ApplicationMessage;
import buptspirit.spm.message.ApplicationState;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.ApplicationEntityPK;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.facade.ApplicationFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SelectedCourseFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import buptspirit.spm.rest.filter.Role;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ApplicationLogic {

    @Inject
    private ApplicationFacade applicationFacade;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private SelectedCourseFacade selectedCourseFacade;

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private MessageMapper messageMapper;


    public ApplicationMessage createApplication(
            SessionMessage sessionMessage,
            int courseId,
            ApplicationCreationMessage applicationCreationMessage) throws ServiceException {
        applicationCreationMessage.enforce();
        boolean alreadyExists = transactional(
                em -> {
                    ApplicationEntityPK pk = new ApplicationEntityPK();
                    pk.setCourseId(courseId);
                    pk.setStudentUserId(sessionMessage.getUserInfo().getId());
                    return applicationFacade.find(em, pk) != null;
                },
                "failed to find application"
        );
        if (alreadyExists)
            throw ServiceError.POST_APPLICATION_ALREADY_APPLIED.toException();
        CourseEntity course = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to create course"
        );
        if (course == null)
            throw ServiceError.POST_APPLICATION_NO_SUCH_COURSE.toException();
        if (course.getFinishDate().before(new Date()))
            throw ServiceError.POST_APPLICATION_COURSE_CAN_NOT_BE_APPLIED.toException();

        ApplicationEntity newApplication = new ApplicationEntity();
        newApplication.setComment(applicationCreationMessage.getComment());
        newApplication.setState(ApplicationState.Waiting.getState());
        newApplication.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        newApplication.setCourseId(courseId);
        newApplication.setStudentUserId(sessionMessage.getUserInfo().getId());
        return transactional(
                em -> {
                    applicationFacade.create(em, newApplication);
                    return messageMapper.intoApplicationMessage(em, newApplication);
                },
                "failed to create application"
        );
    }

    public ApplicationMessage passApplication(int courseId, int studentUserId, SessionMessage sessionMessage) throws ServiceException {
        ApplicationEntity thisApplication = transactional(
                em -> {
                    ApplicationEntityPK pk = new ApplicationEntityPK();
                    pk.setCourseId(courseId);
                    pk.setStudentUserId(studentUserId);
                    return applicationFacade.find(em, pk);
                },
                "failed to find application"
        );
        if (thisApplication == null)
            throw ServiceError.PUT_APPLICATION_NO_SUCH_APPLICATION.toException();
        // check permission
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Administrator.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        if (thisApplication.getState() == ApplicationState.Pass.getState()) {
            return transactional(
                    em -> messageMapper.intoApplicationMessage(em, thisApplication),
                    "failed to edit application"
            );
        } else {
            thisApplication.setState(ApplicationState.Pass.getState());
            // newSelected can not be existed
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
    }

    public ApplicationMessage rejectApplication(int courseId, int studentUserId, SessionMessage sessionMessage) throws ServiceException {
        ApplicationEntity thisApplication = transactional(
                em -> {
                    ApplicationEntityPK pk = new ApplicationEntityPK();
                    pk.setCourseId(courseId);
                    pk.setStudentUserId(studentUserId);
                    return applicationFacade.find(em, pk);
                },
                "failed to find application"
        );
        if (thisApplication == null)
            throw ServiceError.PUT_APPLICATION_NO_SUCH_APPLICATION.toException();
        // check permission
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Administrator.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        if (thisApplication.getState() == ApplicationState.Pass.getState())
            throw ServiceError.PUT_APPLICATION_CAN_NOT_REJECT_APPLICATION_ALREADY_PASSED.toException();
        thisApplication.setState(ApplicationState.Reject.getState());
        return transactional(
                em -> {
                    applicationFacade.edit(em, thisApplication);
                    return messageMapper.intoApplicationMessage(em, thisApplication);
                },
                "failed to edit application"
        );
    }

    public List<ApplicationMessage> getCourseApplication(int courseId, SessionMessage sessionMessage) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse == null)
            throw ServiceError.GET_APPLICATION_NO_SUCH_COURSE.toException();
        if (!sessionMessage.getUserInfo().getRole().equals(Role.Administrator.getName()) &&
                thisCourse.getTeacherUserId() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        return transactional(
                em -> applicationFacade.findByCourseId(em, courseId)
                        .map(application -> messageMapper.intoApplicationMessage(em, application))
                        .collect(Collectors.toList()),
                "failed to find application"
        );
    }

    public List<ApplicationMessage> getStudentApplication(SessionMessage sessionMessage) {
        return transactional(
                em -> applicationFacade.findByStudentId(em, sessionMessage.getUserInfo().getId())
                        .map(application -> messageMapper.intoApplicationMessage(em, application))
                        .collect(Collectors.toList()),
                "failed to find application"
        );
    }

    public ApplicationMessage getStudentCourseApplication(String studentUsername, int courseId) throws ServiceException {
        UserInfoEntity userInfoEntity = transactional(
                em -> userInfoFacade.findByUsername(em, studentUsername),
                "failed to find user"
        );
        if (userInfoEntity == null)
            throw ServiceError.GET_APPLICATION_NO_SUCH_USER.toException();
        return getStudentCourseApplication(userInfoEntity.getUserId(), courseId);
    }

    public ApplicationMessage getStudentCourseApplication(int userId, int courseId) throws ServiceException {
        CourseEntity thisCourse = transactional(
                em -> courseFacade.find(em, courseId),
                "failed to find course"
        );
        if (thisCourse == null)
            throw ServiceError.GET_APPLICATION_NO_SUCH_COURSE.toException();
        ApplicationEntity application = transactional(
                em -> {
                    ApplicationEntityPK pk = new ApplicationEntityPK();
                    pk.setCourseId(courseId);
                    pk.setStudentUserId(userId);
                    return applicationFacade.find(em, pk);
                },
                "failed to find application"
        );
        if (application == null)
            throw ServiceError.GET_APPLICATION_NO_SUCH_APPLICATION.toException();
        return transactional(
                em -> messageMapper.intoApplicationMessage(em, application),
                "failed to convert application to message"
        );
    }
}


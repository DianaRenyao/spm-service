package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.facade.ApplicationFacade;
import buptspirit.spm.rest.filter.ApplicationState;

import javax.inject.Inject;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ApplicationLogic {
    @Inject
    ApplicationFacade applicationFacade;

    @Inject
    private MessageMapper messageMapper;

    public ApplicationCreationMessage createApplication(ApplicationCreationMessage applicationCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException {
        applicationCreationMessage.enforce();
        ApplicationEntity newApplication= new ApplicationEntity();
        newApplication.setComment(applicationCreationMessage.getComment());
        newApplication.setCourseId(applicationCreationMessage.getCourseId());
        newApplication.setState(ApplicationState.Waiting.getState());
        newApplication.setStudentUserId(sessionMessage.getUserInfo().getId());
        return transactional(
                em -> {
                    newApplication.setStudentUserId(sessionMessage.getUserInfo().getId());
                    applicationFacade.create(em, newApplication);
                    return messageMapper.intoApplicationMessage(em, newApplication);
                },
                "failed to create course"
        );
    }
}

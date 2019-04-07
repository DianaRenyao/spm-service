package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.ApplicationMessage;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.facade.ApplicationFacade;
import buptspirit.spm.rest.filter.ApplicationState;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import java.sql.Timestamp;
import java.util.Date;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class ApplicationLogic {
    @Inject
    ApplicationFacade applicationFacade;

    @Inject
    private MessageMapper messageMapper;

    @Inject
    private Logger logger;

    public ApplicationMessage createApplication(ApplicationCreationMessage applicationCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException {
        applicationCreationMessage.enforce();
        ApplicationEntity newApplication= new ApplicationEntity();
        newApplication.setComment(applicationCreationMessage.getComment());
        newApplication.setState(ApplicationState.Waiting.getState());
        newApplication.setTimeCreated( new Timestamp(System.currentTimeMillis()));
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
}

package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.message.ApplicationCreationMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.ApplicationEntity;

public class ApplicationLogic {
    public ApplicationCreationMessage createApplication(ApplicationCreationMessage applicationCreationMessage, SessionMessage sessionMessage) throws ServiceAssertionException {
        applicationCreationMessage.enforce();
        ApplicationEntity applicationEntity= new ApplicationEntity();
        applicationEntity.setComment(applicationCreationMessage.getComment());
        applicationEntity.setCourseId(applicationCreationMessage.getCourseId());
        applicationEntity.setState();
    }
}

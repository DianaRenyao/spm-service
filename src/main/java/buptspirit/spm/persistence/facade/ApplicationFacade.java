package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ApplicationEntity;

public class ApplicationFacade extends AbstractFacade<ApplicationEntity> {

    ApplicationFacade() {
        super(ApplicationEntity.class);
    }
}


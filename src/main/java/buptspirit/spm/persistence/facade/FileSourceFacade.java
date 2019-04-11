package buptspirit.spm.persistence.facade;


import buptspirit.spm.persistence.entity.FileSourceEntity;

import javax.persistence.EntityManager;

public class FileSourceFacade extends AbstractFacade<FileSourceEntity> {

    FileSourceFacade() {
        super(FileSourceEntity.class);
    }

    public FileSourceEntity findByIdentifier(EntityManager em, String identifier) {
        return em.createNamedQuery("FileSource.findByIdentifier", FileSourceEntity.class)
                .setParameter("identifier", identifier)
                .getSingleResult();
    }
}

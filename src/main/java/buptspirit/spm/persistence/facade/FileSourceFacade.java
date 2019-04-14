package buptspirit.spm.persistence.facade;


import buptspirit.spm.persistence.entity.FileSourceEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.stream.Stream;

public class FileSourceFacade extends AbstractFacade<FileSourceEntity> {

    FileSourceFacade() {
        super(FileSourceEntity.class);
    }

    public FileSourceEntity findByIdentifier(EntityManager em, String identifier) {
        try {
            return em.createNamedQuery("FileSource.findByIdentifier", FileSourceEntity.class)
                    .setParameter("identifier", identifier)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Stream<FileSourceEntity> findSectionFile(EntityManager em, int sectionId) {
        return em.createQuery("select f from SectionFileEntity sf " +
                "join FileSourceEntity f on sf.fileSourceId = f.id " +
                "where sf.sectionId = :sectionId", FileSourceEntity.class)
                .setParameter("sectionId", sectionId)
                .getResultStream();
    }
}

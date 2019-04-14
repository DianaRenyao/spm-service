package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.SectionFileEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Singleton
public class SectionFileFacade extends AbstractFacade<SectionFileEntity> {

    public SectionFileFacade() {
        super(SectionFileEntity.class);
    }

    public SectionFileEntity findSectionFileBySequenceAndIdentifier(EntityManager em, int courseId, byte chapterSequence, byte sectionSequence, String identifier) {
        try {
            return em.createQuery("select sf from CourseEntity c " +
                    "join ChapterEntity ch on ch.courseId = c.courseId " +
                    "join SectionEntity s on s.chapterId = ch.chapterId " +
                    "join SectionFileEntity sf on sf.sectionId = s.sectionId " +
                    "join FileSourceEntity fs on fs.id = sf.fileSourceId " +
                    "where c.courseId = :courseId " +
                    "and ch.sequence = :chapterSequence " +
                    "and s.sequence = :sectionSequence " +
                    "and fs.identifier = :identifier", SectionFileEntity.class)
                    .setParameter("courseId", courseId)
                    .setParameter("chapterSequence", chapterSequence)
                    .setParameter("sectionSequence", sectionSequence)
                    .setParameter("identifier", identifier)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

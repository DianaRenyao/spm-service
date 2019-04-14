package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.SectionEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class SectionFacade extends AbstractFacade<SectionEntity> {
    SectionFacade() {
        super(SectionEntity.class);
    }

    public List<SectionEntity> findCourseChapterSections(EntityManager em, int chapterId) {
        return em.createQuery("select s from SectionEntity s where s.chapterId = :chapterId order by s.sequence", SectionEntity.class)
                .setParameter("chapterId", chapterId)
                .getResultList();
    }

    public SectionEntity findCourseChapterSectionByChapterIdAndSequence(EntityManager em, int chapterId, byte sequence) {
        try {
            return em.createQuery("select s from SectionEntity s where s.chapterId = :chapterId and s.sequence = :sequence", SectionEntity.class)
                    .setParameter("chapterId", chapterId)
                    .setParameter("sequence", sequence)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.SectionEntity;

import javax.persistence.EntityManager;
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
}

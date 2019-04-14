package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ChapterEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class ChapterFacade extends AbstractFacade<ChapterEntity> {
    ChapterFacade() {
        super(ChapterEntity.class);
    }

    public List<ChapterEntity> findCourseChapters(EntityManager em, int courseId) {
        return em.createQuery("select c from ChapterEntity c where c.courseId =: courseId order by c.sequence", ChapterEntity.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public ChapterEntity findCourseChapterByCourseIdAndSequence(EntityManager em, int courseId, byte sequence) {
        try {
            return em.createQuery("select c from ChapterEntity c where c.courseId = :courseId and c.sequence = :sequence", ChapterEntity.class)
                    .setParameter("courseId", courseId)
                    .setParameter("sequence", sequence)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

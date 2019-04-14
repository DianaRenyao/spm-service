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

    public SectionEntity findCourseChapterSection(EntityManager em, int courseId, byte chapterSequence, byte sectionSequence) {
        try {
            return em.createQuery("select s from CourseEntity c " +
                    "join ChapterEntity ch on ch.courseId = c.courseId " +
                    "join SectionEntity s on s.chapterId = ch.chapterId " +
                    "where c.courseId = :courseId and ch.sequence = :chapterSequence " +
                    "and s.sequence = :sectionSequence", SectionEntity.class)
                    .setParameter("courseId", courseId)
                    .setParameter("chapterSequence", chapterSequence)
                    .setParameter("sectionSequence", sectionSequence)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

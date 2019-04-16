package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ExamEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class ExamFacade extends AbstractFacade<ExamEntity> {
    public ExamFacade() {
        super(ExamEntity.class);
    }

    public List<ExamEntity> findByCourseId(EntityManager em, int courseId) {
        return em.createQuery("select e from ExamEntity e " +
                "join ChapterEntity ch on ch.chapterId = e.chapterId " +
                "join CourseEntity c on c.courseId = ch.courseId " +
                "where c.courseId = :courseId", ExamEntity.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}

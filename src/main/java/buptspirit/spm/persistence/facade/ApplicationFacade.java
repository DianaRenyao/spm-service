package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ApplicationEntity;

import javax.persistence.EntityManager;
import java.util.stream.Stream;

public class ApplicationFacade extends AbstractFacade<ApplicationEntity> {

    ApplicationFacade() {
        super(ApplicationEntity.class);
    }

    public Stream<ApplicationEntity> findByCourseId(EntityManager em, int courseId) {
        return em.createQuery("select a from ApplicationEntity a where a.courseId = :courseId ", ApplicationEntity.class)
                .setParameter("courseId", courseId)
                .getResultStream();
    }

    public Stream<ApplicationEntity> findByStudentId(EntityManager em, int studentId) {
        return em.createQuery("select a from ApplicationEntity a where a.studentUserId = :studentId ", ApplicationEntity.class)
                .setParameter("studentId", studentId)
                .getResultStream();
    }
}

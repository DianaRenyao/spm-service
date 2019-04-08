package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class SelectedCourseFacade extends AbstractFacade<SelectedCourseEntity>{
    SelectedCourseFacade() {
        super(SelectedCourseEntity.class);
    }

    public SelectedCourseEntity findByCourseIdAndStudentId(EntityManager em, int courseCourseId, int studentUserId){
        try {
            return em.createQuery("select s from SelectedCourseEntity s where s.courseCourseId = :courseCourseId and s.studentUserId = :studentUserId", SelectedCourseEntity.class)
                    .setParameter("courseCourseId", courseCourseId)
                    .setParameter("studentUserId", studentUserId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

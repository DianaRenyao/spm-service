package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.CourseEntity;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Singleton
public class CourseFacade extends AbstractFacade<CourseEntity> {

    public CourseFacade() {
        super(CourseEntity.class);
    }

    public List<CourseEntity> findByTeacherUserId(EntityManager em, int teacherUserId) {
        String query = "select u from CourseEntity u " +
                "where u.teacherUserId = :teacherUserId";
        try {
            return em.createQuery(query, CourseEntity.class)
                    .setParameter("teacherUserId", teacherUserId)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }
}

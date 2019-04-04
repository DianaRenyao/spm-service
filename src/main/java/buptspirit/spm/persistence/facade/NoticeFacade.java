package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.NoticeEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class NoticeFacade extends AbstractFacade<NoticeEntity> {

    NoticeFacade() {
        super(NoticeEntity.class);
    }

    public List<NoticeEntity> findByDateRange(EntityManager em, Timestamp startDate, Timestamp endDate) {
        String query = "select u from NoticeEntity u " +
                "where u.timeCreated between :startDate and :endDate";
        try {
            return em.createQuery(query, NoticeEntity.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}

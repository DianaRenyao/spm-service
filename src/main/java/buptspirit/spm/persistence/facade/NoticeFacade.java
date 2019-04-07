package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.NoticeEntity;
import buptspirit.spm.persistence.entity.TeacherEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import org.javatuples.Pair;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

public class NoticeFacade extends AbstractFacade<NoticeEntity> {

    NoticeFacade() {
        super(NoticeEntity.class);
    }

    public Stream<Pair<NoticeEntity, Pair<TeacherEntity, UserInfoEntity>>> findAllWithAuthor(EntityManager em) {
        return em.createNamedQuery("notice.findAllWithAuthor", Object[].class)
                .getResultStream()
                .map(
                        results -> new Pair<>(
                                (NoticeEntity) results[0],
                                new Pair<>(
                                        (TeacherEntity) results[1],
                                        (UserInfoEntity) results[2]
                                )
                        )
                );
    }

    public Stream<Pair<NoticeEntity, Pair<TeacherEntity, UserInfoEntity>>> findAllWithAuthorRanged(
            EntityManager em, int first, int number
    ) {
        return em.createNamedQuery("notice.findAllWithAuthor", Object[].class)
                .setFirstResult(first)
                .setMaxResults(number)
                .getResultStream()
                .map(
                        results -> new Pair<>(
                                (NoticeEntity) results[0],
                                new Pair<>(
                                        (TeacherEntity) results[1],
                                        (UserInfoEntity) results[2]
                                )
                        )
                );
    }

    public List<NoticeEntity> findByDateRange(EntityManager em, Timestamp startDate, Timestamp endDate) {
        String query = "select u from NoticeEntity u " +
                "where u.timeCreated between :startDate and :endDate";
        return em.createQuery(query, NoticeEntity.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
    }
}

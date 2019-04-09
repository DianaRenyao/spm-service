package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.MessageEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.stream.Stream;

public class MessageFacade extends AbstractFacade<MessageEntity> {

    public MessageFacade() {
        super(MessageEntity.class);
    }

    public Stream<Triplet<MessageEntity, UserInfoEntity, Pair<MessageEntity, UserInfoEntity>>>
    findAllWithAuthorAndReplied(EntityManager em) {
        return em.createNamedQuery("message.findAllWithAuthorAndReplied", Object[].class)
                .getResultList()
                .stream().map(results -> new Triplet<>(
                        (MessageEntity) results[0],
                        (UserInfoEntity) results[1],
                        new Pair<>(
                                (MessageEntity) results[2],
                                (UserInfoEntity) results[3]
                        )
                ));
    }

    public Stream<Triplet<MessageEntity, UserInfoEntity, Pair<MessageEntity, UserInfoEntity>>>
    findAllWithAuthorAndRepliedRanged(EntityManager em, int first, int offset) {
        return em.createNamedQuery("message.findAllWithAuthorAndReplied", Object[].class)
                .setFirstResult(first)
                .setMaxResults(offset)
                .getResultList()
                .stream().map(results -> new Triplet<>(
                        (MessageEntity) results[0],
                        (UserInfoEntity) results[1],
                        new Pair<>(
                                (MessageEntity) results[2],
                                (UserInfoEntity) results[3]
                        )
                ));
    }

    public Stream<Pair<MessageEntity, UserInfoEntity>>
    findAllWithAuthor(EntityManager em) {
        return em.createNamedQuery("message.findAllWithAuthor", Object[].class)
                .getResultList()
                .stream().map(results -> new Pair<>(
                        (MessageEntity) results[0],
                        (UserInfoEntity) results[1]
                ));
    }

    public Stream<Pair<MessageEntity, UserInfoEntity>>
    findAllWithAuthorRanged(EntityManager em, int first, int offset) {
        return em.createNamedQuery("message.findAllWithAuthor", Object[].class)
                .setFirstResult(first)
                .setMaxResults(offset)
                .getResultList()
                .stream().map(results -> new Pair<>(
                        (MessageEntity) results[0],
                        (UserInfoEntity) results[1]
                ));
    }

    public Triplet<MessageEntity, UserInfoEntity, Pair<MessageEntity, UserInfoEntity>>
    findWithAuthorAndReplied(EntityManager em, int messageId) {
        try {
            Object[] results = em.createNamedQuery("message.findWithAuthorAndReplied", Object[].class)
                    .setParameter("messageId", messageId)
                    .getSingleResult();
            return new Triplet<>(
                    (MessageEntity) results[0],
                    (UserInfoEntity) results[1],
                    new Pair<>(
                            (MessageEntity) results[2],
                            (UserInfoEntity) results[3]
                    )
            );
        } catch (NoResultException e) {
            return null;
        }
    }
}

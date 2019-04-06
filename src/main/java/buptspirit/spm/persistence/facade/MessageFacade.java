package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.MessageEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;

import javax.persistence.EntityManager;
import java.util.stream.Stream;

public class MessageFacade extends AbstractFacade<MessageEntity> {

    public MessageFacade() {
        super(MessageEntity.class);
    }

    public Stream<MessageWithAuthorAndReplied> findAllWithAuthorAndReplied(EntityManager em) {
        return em.createQuery(
                "SELECT m, u, r, ru FROM MessageEntity m " +
                        "JOIN UserInfoEntity u ON m.author = u.id " +
                        "LEFT OUTER JOIN MessageEntity r ON m.replyTo = r.id " +
                        "LEFT OUTER JOIN UserInfoEntity ru ON r.author = ru.id",
                Object[].class)
                .getResultList()
                .stream().map(
                        results -> {
                            MessageWithAuthorAndReplied result = new MessageWithAuthorAndReplied();
                            result.message = (MessageEntity) results[0];
                            result.userInfo = (UserInfoEntity) results[1];
                            result.replied = (MessageEntity) results[2];
                            result.repliedUserInfo = (UserInfoEntity) results[3];
                            return result;
                        }
                );
    }

    public class MessageWithAuthorAndReplied {
        public MessageEntity message;
        public UserInfoEntity userInfo;
        public MessageEntity replied;
        public UserInfoEntity repliedUserInfo;
    }
}

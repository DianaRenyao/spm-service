package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.message.MessageCreationMessage;
import buptspirit.spm.message.MessageMessage;
import buptspirit.spm.message.MessageWithRepliedMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.MessageEntity;
import buptspirit.spm.persistence.facade.MessageFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

public class MessageLogic {

    @Inject
    private MessageFacade messageFacade;

    @Inject
    private UserInfoFacade userInfoFacade;

    public List<MessageMessage> getAllMessage() {
        return transactional(
                em -> messageFacade.findAllWithAuthor(em)
                        .map(MessageMessage::fromEntity)
                        .collect(Collectors.toList()),
                "failed to find messages"
        );
    }

    public List<MessageWithRepliedMessage> getAllMessageWithReplied() {
        return transactional(
                em -> messageFacade.findAllWithAuthorAndReplied(em)
                        .map(MessageWithRepliedMessage::fromEntity)
                        .collect(Collectors.toList()),
                "failed to find messages"
        );
    }

    public List<MessageMessage> getAllMessageRanged(int first, int offset) {
        return transactional(
                em -> messageFacade.findAllWithAuthorRanged(em, first, offset)
                        .map(MessageMessage::fromEntity)
                        .collect(Collectors.toList()),
                "failed to find messages"
        );
    }

    public List<MessageWithRepliedMessage> getAllMessageWithRepliedRanged(int first, int offset) {
        return transactional(
                em -> messageFacade.findAllWithAuthorAndRepliedRanged(em, first, offset)
                        .map(MessageWithRepliedMessage::fromEntity)
                        .collect(Collectors.toList()),
                "failed to find messages"
        );
    }

    public MessageMessage createMessage(SessionMessage sessionMessage, MessageCreationMessage creationMessage)
            throws ServiceAssertionException {
        creationMessage.enforce();
        MessageEntity entity = new MessageEntity();
        entity.setAuthor(sessionMessage.getUserInfo().getId());
        entity.setContent(creationMessage.getContent());
        entity.setReplyTo(creationMessage.getReplyTo());
        transactional(
                em -> {
                    messageFacade.create(em, entity);
                    return null;
                },
                "failed to create message "
        );
        return MessageMessage.fromEntity(entity, sessionMessage.getUserInfo());
    }
}

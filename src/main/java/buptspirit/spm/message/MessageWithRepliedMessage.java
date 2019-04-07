package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.MessageEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Date;

public class MessageWithRepliedMessage {
    private int messageId;
    private PublicUserInfoMessage author;
    private String content;
    private Date timeCreated;
    private MessageMessage replyToMessage;

    public static MessageWithRepliedMessage fromEntity(
            Triplet<MessageEntity, UserInfoEntity, Pair<MessageEntity, UserInfoEntity>> entities) {
        MessageWithRepliedMessage message = new MessageWithRepliedMessage();
        message.setMessageId(entities.getValue0().getMessageId());
        message.setAuthor(PublicUserInfoMessage.fromEntity(entities.getValue1()));
        message.setContent(entities.getValue0().getContent());
        message.setTimeCreated(entities.getValue0().getTimeCreated());
        if (entities.getValue2().getValue0() != null) {
            message.setReplyToMessage(MessageMessage.fromEntity(entities.getValue2()));
        }
        return message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public PublicUserInfoMessage getAuthor() {
        return author;
    }

    public void setAuthor(PublicUserInfoMessage author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public MessageMessage getReplyToMessage() {
        return replyToMessage;
    }

    public void setReplyToMessage(MessageMessage replyToMessage) {
        this.replyToMessage = replyToMessage;
    }
}

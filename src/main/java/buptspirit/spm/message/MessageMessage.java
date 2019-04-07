package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.MessageEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import org.javatuples.Pair;

import java.util.Date;

public class MessageMessage {
    private int messageId;
    private PublicUserInfoMessage author;
    private String content;
    private Date timeCreated;
    private Integer replyTo;

    public static MessageMessage fromEntity(Pair<MessageEntity, UserInfoEntity> entities) {
        MessageMessage message = new MessageMessage();
        message.setMessageId(entities.getValue0().getMessageId());
        message.setAuthor(PublicUserInfoMessage.fromEntity(entities.getValue1()));
        message.setContent(entities.getValue0().getContent());
        message.setTimeCreated(entities.getValue0().getTimeCreated());
        message.setReplyTo(entities.getValue0().getReplyTo());
        return message;
    }

    public static MessageMessage fromEntity(MessageEntity entity, UserInfoMessage userInfoMessage) {
        MessageMessage message = new MessageMessage();
        message.setMessageId(entity.getMessageId());
        message.setAuthor(PublicUserInfoMessage.fromUserInfoMessage(userInfoMessage));
        message.setContent(entity.getContent());
        message.setTimeCreated(entity.getTimeCreated());
        message.setReplyTo(entity.getReplyTo());
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

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }
}

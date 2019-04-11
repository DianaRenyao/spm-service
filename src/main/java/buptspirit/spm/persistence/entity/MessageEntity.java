package buptspirit.spm.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "spm")
@NamedQueries({
        @NamedQuery(name = "message.findWithAuthorAndReplied",
                query = "SELECT m, u, r, ru FROM MessageEntity m " +
                        "JOIN UserInfoEntity u ON m.author = u.id " +
                        "LEFT OUTER JOIN MessageEntity r ON m.replyTo = r.id " +
                        "LEFT OUTER JOIN UserInfoEntity ru ON r.author = ru.id " +
                        "WHERE m.messageId = :messageId " +
                        "ORDER BY m.messageId DESC"),
        @NamedQuery(name = "message.findAllWithAuthorAndReplied",
                query = "SELECT m, u, r, ru FROM MessageEntity m " +
                        "JOIN UserInfoEntity u ON m.author = u.id " +
                        "LEFT OUTER JOIN MessageEntity r ON m.replyTo = r.id " +
                        "LEFT OUTER JOIN UserInfoEntity ru ON r.author = ru.id " +
                        "ORDER BY m.messageId DESC"),
        @NamedQuery(name = "message.findAllWithAuthor",
                query = "SELECT m, u FROM MessageEntity m " +
                        "JOIN UserInfoEntity u ON m.author = u.id " +
                        "ORDER BY m.messageId DESC"),
})
public class MessageEntity {
    private int messageId;
    private int author;
    private String content;
    private Timestamp timeCreated;
    private Integer replyTo;

    @Id
    @GeneratedValue
    @Column(name = "message_id", nullable = false)
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "author", nullable = false)
    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    @Basic
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "time_created", nullable = false)
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "reply_to", nullable = true)
    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return messageId == that.messageId &&
                author == that.author &&
                Objects.equals(content, that.content) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(replyTo, that.replyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, author, content, timeCreated, replyTo);
    }

    @PrePersist
    protected void prePersist() {
        this.setTimeCreated(new Timestamp(System.currentTimeMillis()));
    }
}

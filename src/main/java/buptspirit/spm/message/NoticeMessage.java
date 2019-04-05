package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.NoticeEntity;

import java.util.Date;

public class NoticeMessage {

    private int noticeId;
    private TeacherMessage author;
    private String title;
    private String detail;
    private Date timeCreated;

    public static NoticeMessage fromEntity(NoticeEntity entity, TeacherMessage author) {
        NoticeMessage message = new NoticeMessage();
        message.setNoticeId(entity.getNoticeId());
        message.setAuthor(author);
        message.setTitle(entity.getTitle());
        message.setDetail(entity.getDetail());
        message.setTimeCreated(entity.getTimeCreated());
        return message;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public TeacherMessage getAuthor() {
        return author;
    }

    public void setAuthor(TeacherMessage author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}

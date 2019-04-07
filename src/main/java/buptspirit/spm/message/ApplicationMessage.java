package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ApplicationEntity;

import java.util.Date;

public class ApplicationMessage {
    private CourseSummaryMessage courseSummary;
    private String comment;
    private StudentMessage student;
    private Date timeCreated;
    private byte state;

    public static ApplicationMessage fromEntity(ApplicationEntity applicationEntity, CourseSummaryMessage courseMessage, StudentMessage studentMessage) {
        ApplicationMessage applicationMessage = new ApplicationMessage();
        applicationMessage.setTimeCreated(applicationEntity.getTimeCreated());
        applicationMessage.setComment(applicationEntity.getComment());
        applicationMessage.setState(applicationEntity.getState());
        applicationMessage.setStudent(studentMessage);
        applicationMessage.setCourseSummary(courseMessage);
        return applicationMessage;
    }

    public CourseSummaryMessage getCourseSummary() {
        return courseSummary;
    }

    public void setCourseSummary(CourseSummaryMessage courseSummary) {
        this.courseSummary = courseSummary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StudentMessage getStudent() {
        return student;
    }

    public void setStudent(StudentMessage student) {
        this.student = student;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date time_created) {
        this.timeCreated = time_created;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ApplicationEntity;

import java.util.Date;

public class ApplicationMessage {
    private CourseMessage courseMessage;
    private String comment;
    private StudentMessage student;
    private Date time_created;
    private byte state;

    public static ApplicationMessage fromEntity(ApplicationEntity applicationEntity, CourseMessage courseMessage, StudentMessage studentMessage){
        ApplicationMessage applicationMessage = new ApplicationMessage();
        applicationMessage.setTime_created(applicationEntity.getTimeCreated());
        applicationMessage.setComment(applicationEntity.getComment());
        applicationMessage.setState(applicationEntity.getState());
        applicationMessage.setStudent(studentMessage);
        applicationMessage.setCourseMessage(courseMessage);
        return applicationMessage;
    }

    public CourseMessage getCourseMessage() {
        return courseMessage;
    }

    public void setCourseMessage(CourseMessage courseMessage) {
        this.courseMessage = courseMessage;
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

    public Date getTime_created() {
        return time_created;
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.UserInfoEntity;

import java.sql.Date;

public class OptionalCourseMessage {

    private String courseName;
    private String descriptionSummary;
    private String teacherName;
    private byte period;
    private Date startTime;
    private Date finishTime;

    public String getCourseName() {
        return courseName;
    }

    public String getDescriptionSummary() {
        return descriptionSummary;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public byte getPeriod() {
        return period;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescriptionSummary(String descriptionSummary) {
        this.descriptionSummary = descriptionSummary;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setPeriod(byte period) {
        this.period = period;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import java.sql.Date;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class CourseMessage implements InputMessage {

    private String courseName;
    private String description;
    private String teacherUsername;
    private String teacherRealName;
    private byte period;
    private Date startDate;
    private Date finishDate;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(courseName != null && !courseName.isEmpty());
        serviceAssert(description != null && !description.isEmpty());
        serviceAssert(teacherUsername != null && !teacherUsername.isEmpty());
        serviceAssert(period != 0);
        serviceAssert(startDate != null);
        serviceAssert(finishDate != null);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getTeacherRealName() {
        return teacherRealName;
    }

    public void setTeacherRealName(String teacherRealName) {
        this.teacherRealName = teacherRealName;
    }

    public byte getPeriod() {
        return period;
    }

    public void setPeriod(byte period) {
        this.period = period;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}

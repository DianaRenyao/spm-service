package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.persistence.entity.CourseEntity;

import java.sql.Date;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class CourseCreationMessage implements InputMessage {

    private String courseName;
    private String description;
    private byte period;
    private Date startDate;
    private Date finishDate;

    public static CourseCreationMessage fromEntity(CourseEntity entity, UserInfoMessage user) {
        CourseCreationMessage courseCreationMessage = new CourseCreationMessage();
        courseCreationMessage.setStartDate(entity.getStartDate());
        courseCreationMessage.setFinishDate(entity.getFinishDate());
        courseCreationMessage.setCourseName(entity.getCourseName());
        courseCreationMessage.setDescription(entity.getDescription());
        courseCreationMessage.setPeriod(entity.getPeriod());
        return courseCreationMessage;
    }

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(courseName != null && !courseName.isEmpty());
        serviceAssert(description != null && !description.isEmpty());
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

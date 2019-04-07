package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.CourseEntity;

import java.sql.Date;

public class CourseSummaryMessage {

    private int courseId;
    private String courseName;
    private String descriptionSummary;
    private TeacherMessage teacher;
    private byte period;
    private Date startDate;
    private Date finishDate;

    public static CourseSummaryMessage fromEntity(CourseEntity entity, TeacherMessage teacher) {
        CourseSummaryMessage courseMessage = new CourseSummaryMessage();
        courseMessage.setCourseId(entity.getCourseId());
        courseMessage.setStartDate(entity.getStartDate());
        courseMessage.setFinishDate(entity.getFinishDate());
        courseMessage.setCourseName(entity.getCourseName());
        if (entity.getDescription().length() >= 256)
            courseMessage.setDescriptionSummary(entity.getDescription().substring(0, 256));
        else
            courseMessage.setDescriptionSummary(entity.getDescription());
        courseMessage.setPeriod(entity.getPeriod());
        courseMessage.setTeacher(teacher);
        return courseMessage;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescriptionSummary() {
        return descriptionSummary;
    }

    public void setDescriptionSummary(String descriptionSummary) {
        this.descriptionSummary = descriptionSummary;
    }

    public TeacherMessage getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherMessage teacher) {
        this.teacher = teacher;
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

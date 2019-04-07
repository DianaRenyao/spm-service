package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.CourseEntity;

import java.sql.Date;

public class CourseMessage {

    private int courseId;
    private String courseName;
    private String description;
    private TeacherMessage teacher;
    private byte period;
    private Date startDate;
    private Date finishDate;

    public static CourseMessage fromEntity(CourseEntity entity, TeacherMessage teacher) {
        CourseMessage courseMessage = new CourseMessage();
        courseMessage.setCourseId(entity.getCourseId());
        courseMessage.setStartDate(entity.getStartDate());
        courseMessage.setFinishDate(entity.getFinishDate());
        courseMessage.setCourseName(entity.getCourseName());
        courseMessage.setDescription(entity.getDescription());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

package buptspirit.spm.message;
import buptspirit.spm.persistence.entity.CourseEntity;
import java.sql.Date;

public class CourseMessage {
    private String courseName;
    private String descriptionSub;
    private String teacherRealName;
    private byte period;
    private Date startDate;
    private Date finishDate;

    public static CourseMessage fromEntity(CourseEntity entity, UserInfoMessage user) {
        CourseMessage CourseMessage = new CourseMessage();
        CourseMessage.setStartDate(entity.getStartDate());
        CourseMessage.setFinishDate(entity.getFinishDate());
        CourseMessage.setCourseName(entity.getCourseName());
        if(entity.getDescription().length()>=256)
            CourseMessage.setDescriptionSub(entity.getDescription().substring(0,255));
        else
            CourseMessage.setDescriptionSub(entity.getDescription());
        CourseMessage.setPeriod(entity.getPeriod());
        CourseMessage.setTeacherRealName(user.getRealName());
        return CourseMessage;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return descriptionSub;
    }

    public void setDescriptionSub(String descriptionSub) {
        this.descriptionSub = descriptionSub;
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

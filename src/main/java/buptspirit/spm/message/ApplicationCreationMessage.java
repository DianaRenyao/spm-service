package buptspirit.spm.message;

public class ApplicationCreationMessage implements InputMessage{
    private int courseId;
    private String comment;

    @Override
    public void enforce() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

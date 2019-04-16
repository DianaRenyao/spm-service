package buptspirit.spm.message;

public class ApplicationCreationMessage implements InputMessage {
    private String comment;

    @Override
    public void enforce() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class MessageCreationMessage implements InputMessage {

    private String content;
    // maybe null
    private Integer replyTo;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(content != null && !content.isEmpty());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }
}

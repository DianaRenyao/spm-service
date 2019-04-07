package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class MessageModificationMessage implements InputMessage {
    private String content;

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
}

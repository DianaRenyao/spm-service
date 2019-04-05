package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class NoticeCreationMessage implements InputMessage {

    private String title;
    private String detail;

    public void enforce() throws ServiceAssertionException {
        serviceAssert(title != null && !title.isEmpty());
        serviceAssert(detail != null && !detail.isEmpty());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

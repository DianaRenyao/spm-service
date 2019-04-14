package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class ChapterEditingMessage implements InputMessage {

    private String chapterName;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(chapterName != null && !chapterName.isEmpty());
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}

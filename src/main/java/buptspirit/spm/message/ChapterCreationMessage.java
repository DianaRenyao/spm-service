package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class ChapterCreationMessage implements InputMessage {

    private String chapterName;
    private byte sequence;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(chapterName != null && !chapterName.isEmpty());
        serviceAssert(sequence >= 0);
    }


    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }
}

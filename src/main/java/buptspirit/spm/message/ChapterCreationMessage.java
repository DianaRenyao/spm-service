package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class ChapterCreationMessage implements InputMessage {

    private String courseName;
    private byte sequence;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(courseName != null && !courseName.isEmpty());
        serviceAssert(sequence!=0);
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class SectionCreationMessage implements InputMessage {
    private String sectionName;
    private byte sequence;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(sectionName != null && !sectionName.isEmpty());
        serviceAssert(sequence >= 0);
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }
}

package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class SectionEditingMessage implements InputMessage {
    private String sectionName;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(sectionName != null && !sectionName.isEmpty());
    }
}

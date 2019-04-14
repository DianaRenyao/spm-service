package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.SectionEntity;

import java.util.List;

public class SectionMessage {
    private int sectionId;
    private int chapterId;
    private byte sequence;
    private String sectionName;
    private List<FileSourceMessage> fileSources;

    public static SectionMessage fromEntity(SectionEntity entity, List<FileSourceMessage> fileSourceMessages) {
        SectionMessage sectionMessage = new SectionMessage();
        sectionMessage.setChapterId(entity.getChapterId());
        sectionMessage.setSequence(entity.getSequence());
        sectionMessage.setSectionId(entity.getSectionId());
        sectionMessage.setSectionName(entity.getSectionName());
        sectionMessage.setFileSources(fileSourceMessages);
        return sectionMessage;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<FileSourceMessage> getFileSources() {
        return fileSources;
    }

    public void setFileSources(List<FileSourceMessage> fileSources) {
        this.fileSources = fileSources;
    }
}

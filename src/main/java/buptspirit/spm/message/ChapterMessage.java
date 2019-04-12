package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ChapterEntity;

import java.util.List;

public class ChapterMessage {
    private int chapterId;
    private int courseId;
    private byte sequence;
    private String chapterName;
    private List<SectionMessage> sections;

    public static ChapterMessage fromEntity(ChapterEntity chapterEntity, List<SectionMessage> sections) {
        ChapterMessage chapterMessage = new ChapterMessage();
        chapterMessage.setChapterId(chapterEntity.getChapterId());
        chapterMessage.setSequence(chapterEntity.getSequence());
        chapterMessage.setChapterName(chapterEntity.getChapterName());
        chapterMessage.setCourseId(chapterEntity.getCourseId());
        chapterMessage.setSections(sections);
        return chapterMessage;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public byte getSequence() {
        return sequence;
    }

    public void setSequence(byte sequence) {
        this.sequence = sequence;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<SectionMessage> getSections() {
        return sections;
    }

    public void setSections(List<SectionMessage> sections) {
        this.sections = sections;
    }
}

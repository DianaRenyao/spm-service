package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ChapterEntity;

public class ChapterMessage {
    private int chapterId;
    private int courseId;
    private byte sequence;
    private String chapterName;

    public static ChapterMessage fromEntity(ChapterEntity chapterEntity) {
        ChapterMessage chapterMessage = new ChapterMessage();
        chapterMessage.setChapterId(chapterEntity.getChapterId());
        chapterMessage.setSequence(chapterEntity.getSequence());
        chapterMessage.setChapterName(chapterEntity.getChapterName());
        chapterMessage.setCourseId(chapterEntity.getCourseId());
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
}

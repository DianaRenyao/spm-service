package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.ChapterEntity;

public class ChapterSummaryMessage {
    byte sequence;
    String chapterName;

    public static ChapterSummaryMessage fromEntity(ChapterEntity entity) {
        ChapterSummaryMessage chapter = new ChapterSummaryMessage();
        chapter.setSequence(entity.getSequence());
        chapter.setChapterName(entity.getChapterName());
        return chapter;
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

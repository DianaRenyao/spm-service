package buptspirit.spm.message;

import buptspirit.spm.persistence.entity.FileSourceEntity;

public class FileSourceMessage {
    private int fileSourceId;
    private String filename;
    private String identifier;
    private String fileType;

    public static FileSourceMessage fromEntity(FileSourceEntity entity){
        FileSourceMessage fileSourceMessage = new FileSourceMessage();
        fileSourceMessage.setFilename(entity.getFilename());
        fileSourceMessage.setFileSourceId(entity.getFileSourceId());
        fileSourceMessage.setFileType(entity.getFileType());
        fileSourceMessage.setIdentifier(entity.getIdentifier());
        return fileSourceMessage;
    }

    public int getFileSourceId() {
        return fileSourceId;
    }

    public void setFileSourceId(int fileSourceId) {
        this.fileSourceId = fileSourceId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

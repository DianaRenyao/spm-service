package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file_source", schema = "spm", catalog = "")
public class FileSourceEntity {
    private int fileSourceId;
    private String filename;
    private String identifier;
    private String fileType;

    @Id
    @Column(name = "file_source_id")
    public int getFileSourceId() {
        return fileSourceId;
    }

    public void setFileSourceId(int fileSourceId) {
        this.fileSourceId = fileSourceId;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "identifier")
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Basic
    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSourceEntity that = (FileSourceEntity) o;
        return fileSourceId == that.fileSourceId &&
                Objects.equals(filename, that.filename) &&
                Objects.equals(identifier, that.identifier) &&
                Objects.equals(fileType, that.fileType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileSourceId, filename, identifier, fileType);
    }
}

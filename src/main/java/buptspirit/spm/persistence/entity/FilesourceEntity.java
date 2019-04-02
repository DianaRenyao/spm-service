package buptspirit.spm.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "filesource", schema = "spm", catalog = "")
public class FilesourceEntity {
    private int filesouceId;
    private String filename;
    private String identifier;
    private String filetype;

    @Id
    @Column(name = "filesouce_id")
    public int getFilesouceId() {
        return filesouceId;
    }

    public void setFilesouceId(int filesouceId) {
        this.filesouceId = filesouceId;
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
    @Column(name = "filetype")
    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilesourceEntity that = (FilesourceEntity) o;
        return filesouceId == that.filesouceId &&
                Objects.equals(filename, that.filename) &&
                Objects.equals(identifier, that.identifier) &&
                Objects.equals(filetype, that.filetype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filesouceId, filename, identifier, filetype);
    }
}

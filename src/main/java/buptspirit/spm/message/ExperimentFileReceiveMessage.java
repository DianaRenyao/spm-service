package buptspirit.spm.message;

public class ExperimentFileReceiveMessage {
    private int experimentId;
    private String fileIdentifier;


    public String getFileIdentifier() {
        return fileIdentifier;
    }

    public void setFileIdentifier(String fileIdentifier) {
        this.fileIdentifier = fileIdentifier;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }
}

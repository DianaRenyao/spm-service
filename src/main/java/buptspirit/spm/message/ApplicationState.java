package buptspirit.spm.message;

public enum ApplicationState {
    Waiting((byte) 1),
    Pass((byte) 2),
    Reject((byte) 3);

    private byte state;

    ApplicationState(byte state) {
        this.state = state;
    }

    public byte getState() {
        return state;
    }
}

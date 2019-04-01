package buptspirit.spm.rest.exception;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 4372068319488842734L;

    private static final Response.Status DEFAULT_STATUS = Response.Status.BAD_REQUEST;

    private Message message;

    public ServiceException(String message) {
        this(DEFAULT_STATUS, message);
    }

    public ServiceException(Response.Status status, String message) {
        super(message);
        this.message = new Message();
        this.message.setStatus(status.getStatusCode());
        this.message.setMessage(message);
    }

    @Override
    public String getMessage() {
        return message.getMessage();
    }

    public Message getFullMessage() {
        return message;
    }

    @XmlRootElement
    public class Message {
        private int status;
        private String message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

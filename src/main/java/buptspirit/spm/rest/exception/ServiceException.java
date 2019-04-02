package buptspirit.spm.rest.exception;

import buptspirit.spm.rest.message.ErrorMessage;

import javax.ws.rs.core.Response;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 4372068319488842734L;

    private static final Response.Status DEFAULT_STATUS = Response.Status.BAD_REQUEST;

    private ErrorMessage errorMessage;

    public ServiceException(String message) {
        this(DEFAULT_STATUS, message);
    }

    public ServiceException(Response.Status status, String message) {
        super(message);
        this.errorMessage = new ErrorMessage();
        this.errorMessage.setStatus(status.getStatusCode());
        this.errorMessage.setMessage(message);
    }

    @Override
    public String getMessage() {
        return errorMessage.getMessage();
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }


}

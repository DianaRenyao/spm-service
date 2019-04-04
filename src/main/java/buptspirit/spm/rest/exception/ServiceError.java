package buptspirit.spm.rest.exception;

import buptspirit.spm.rest.message.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public enum ServiceError {
    // unknown error
    UNKNOWN(0, Response.Status.INTERNAL_SERVER_ERROR, "server internal error occurred, refer to the team bupt-spirit"),
    // raw http error, do not use
    RAW_HTTP_ERROR(1, Response.Status.INTERNAL_SERVER_ERROR, "raw http error, refer to status"),
    // authentication or authorization error
    UNAUTHENTICATED(10001, Response.Status.UNAUTHORIZED, "client must login to get access rights to the resource"),
    FORBIDDEN(10002, Response.Status.FORBIDDEN, "client does not have access rights to the resource"),
    INVALID_USERNAME_OR_PASSWORD(11001, Response.Status.UNAUTHORIZED, "username or password provided is invalid"),
    ;

    private int code;
    private Response.Status status;
    private String message;

    ServiceError(int code, Response.Status status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Response toResponse() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(getCode());
        errorMessage.setStatus(getStatus().getStatusCode());
        errorMessage.setMessage(getMessage());
        return Response.status(getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public ServiceException toException() {
        return new ServiceException(this);
    }
}

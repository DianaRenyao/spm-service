package buptspirit.spm.exception;

import buptspirit.spm.message.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public enum ServiceError {
    /* unknown errors */
    UNKNOWN(0, Response.Status.INTERNAL_SERVER_ERROR, "server internal error occurred, refer to the team bupt-spirit"),

    /* raw http error, do not use */
    RAW_HTTP_ERROR(1, Response.Status.INTERNAL_SERVER_ERROR, "raw http error, refer to status"),

    /* authentication or authorization errors */
    UNAUTHENTICATED(10001, Response.Status.UNAUTHORIZED, "client must login to get access rights to the resource"),
    FORBIDDEN(10002, Response.Status.FORBIDDEN, "client does not have access rights to the resource"),

    /* session resource errors */
    // enforcement
    LOGIN_USERNAME_IS_EMPTY(20001, Response.Status.BAD_REQUEST, "username is empty"),
    LOGIN_PASSWORD_IS_EMPTY(20002, Response.Status.BAD_REQUEST, "password is empty"),
    // logic
    LOGIN_INVALID_USERNAME_OR_PASSWORD(21001, Response.Status.UNAUTHORIZED, "username or password provided is invalid"),

    /* student resource errors */
    POST_STUDENT_USERNAME_IS_EMPTY(20001, Response.Status.BAD_REQUEST, "username is empty"),
    POST_STUDENT_PASSWORD_IS_EMPTY(20002, Response.Status.BAD_REQUEST, "password is empty"),
    POST_STUDENT_REAL_NAME_IS_EMPTY(20003, Response.Status.BAD_REQUEST, "real name is empty"),
    POST_STUDENT_EMAIL_IS_EMPTY(20004, Response.Status.BAD_REQUEST, "email is empty"),
    POST_STUDENT_PHONE_IS_EMPTY(20005, Response.Status.BAD_REQUEST, "phone is empty"),
    POST_STUDENT_CLAZZ_IS_EMPTY(20006, Response.Status.BAD_REQUEST, "clazz is empty"),
    POST_STUDENT_USERNAME_IS_NOT_AN_ID(20007, Response.Status.BAD_REQUEST, "username is not an student or teacher id"),
    POST_STUDENT_USERNAME_ALREADY_EXISTS(20101, Response.Status.BAD_REQUEST, "username already exists"),
    GET_STUDENT_USERNAME_IS_EMPTY(21001, Response.Status.BAD_REQUEST, "username is empty"),
    GET_STUDENT_NO_SUCH_STUDENT(21101, Response.Status.BAD_REQUEST, "no such user"),
    GET_STUDENT_NO_PERMISSION(21102, Response.Status.FORBIDDEN, "no permission to get student information"),
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

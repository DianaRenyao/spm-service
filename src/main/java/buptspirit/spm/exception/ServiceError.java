package buptspirit.spm.exception;

import buptspirit.spm.message.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public enum ServiceError {
    /* unknown errors */
    UNKNOWN(0, Response.Status.INTERNAL_SERVER_ERROR, "server internal error occurred, refer to the team bupt-spirit"),

    /* raw http error, do not use */
    RAW_HTTP_ERROR(1, Response.Status.INTERNAL_SERVER_ERROR, "raw http error, refer to status"),
    /* assertion error, do not use */
    ASSERTION(2, Response.Status.BAD_REQUEST, "assertion error"),

    /* authentication or authorization errors */
    UNAUTHENTICATED(10001, Response.Status.UNAUTHORIZED, "client must login to get access rights to the resource"),
    FORBIDDEN(10002, Response.Status.FORBIDDEN, "client does not have access rights to the resource"),

    // third position
    // GET - 1
    // POST - 2
    // PUT - 3
    // DELETE - 4

    /* session resource errors */
    POST_SESSION_INVALID_USERNAME_OR_PASSWORD(20201, Response.Status.UNAUTHORIZED, "usernamem provided is invalid"),

    /* student resource errors */
    GET_STUDENT_NO_SUCH_STUDENT(30101, Response.Status.BAD_REQUEST, "no such user"),
    POST_STUDENT_USERNAME_ALREADY_EXISTS(30201, Response.Status.BAD_REQUEST, "username already exists"),

    /* notice resource errors */
    GET_NOTICE_NO_SUCH_NOTICE(40101, Response.Status.BAD_REQUEST, "no such notice"),
    PUT_NOTICE_NO_SUCH_NOTICE(40302, Response.Status.BAD_REQUEST, "no such notice"),
    DELETE_NOTICE_NO_SUCH_NOTICE(40403, Response.Status.BAD_REQUEST, "no such notice"),

    /* teacher resource errors */
    GET_TEACHER_NO_SUCH_TEACHER(50101, Response.Status.BAD_REQUEST, "no such user"),
    POST_TEACHER_USERNAME_ALREADY_EXISTS(50201, Response.Status.BAD_REQUEST, "username already exists"),

    /* course resource errors*/
    GET_COURSE_NO_SUCH_COURSE(60101, Response.Status.BAD_REQUEST, "no such course"),

    //POST_COURSE_ALREADY_EXISTS(60201, Response.Status.BAD_REQUEST, "course already exists"), // course never already exists

    /* message resource error */
//    GET_MESSAGE_NO_SUCH_MESSAGE(70101, Response.Status.BAD_REQUEST, "no such message"),
//    PUT_MESSAGE_NO_SUCH_MESSAGE(70301, Response.Status.BAD_REQUEST, "no such message"),
//    PUT_MESSAGE_UNAUTHORIZED(70302, Response.Status.UNAUTHORIZED, "unauthorized modification for message"),
//    DELETE_MESSAGE_UNAUTHORIZED(70404, Response.Status.UNAUTHORIZED, "unauthorized deletion for message"),
//    DELETE_MESSAGE_NO_SUCH_MESSAGE(70405, Response.Status.BAD_REQUEST, "no such message"),

    /* application resource errors*/
    GET_APPLICATION_NO_SUCH_COURSE(80101, Response.Status.BAD_REQUEST, "no such course"),
    GET_APPLICATION_NO_SUCH_APPLICATION(80102, Response.Status.BAD_REQUEST, "no such application"),
    GET_APPLICATION_NO_SUCH_USER(80103, Response.Status.BAD_REQUEST, "no such user"),
    POST_APPLICATION_NO_SUCH_COURSE(80201, Response.Status.BAD_REQUEST, "no such course"),
    POST_APPLICATION_COURSE_CAN_NOT_BE_APPLIED(80202, Response.Status.BAD_REQUEST, "course can not be applied"),
    POST_APPLICATION_ALREADY_APPLIED(80203, Response.Status.BAD_REQUEST, "already applied"),
    PUT_APPLICATION_NO_SUCH_APPLICATION(80301, Response.Status.BAD_REQUEST, "no such application"),
    PUT_APPLICATION_CAN_NOT_REJECT_APPLICATION_ALREADY_PASSED(80302, Response.Status.BAD_REQUEST, "can not reject accepted application"),

    /* chapter resource errors */
    POST_CHAPTER_CAN_NOT_BE_INSERTED(90201, Response.Status.BAD_REQUEST, "chapter can not be inserted"),
    POST_CHAPTER_COURSE_DO_NOT_EXISTS(90202, Response.Status.BAD_REQUEST, "course do not exist"),
    PUT_CHAPTER_NO_SUCH_CHAPTER(90301, Response.Status.BAD_REQUEST, "no such chapter"),
    DELETE_CHAPTER_COURSE_DO_NOT_EXISTS(90401, Response.Status.BAD_REQUEST, "course do not exist"),

    POST_SECTION_CAN_NOT_BE_INSERTED(100201, Response.Status.BAD_REQUEST, "section can not be inserted"),
    POST_SECTION_CHAPTER_DO_NOT_EXISTS(100202, Response.Status.BAD_REQUEST, "chapter do not exist"),
    POST_SECTION_COURSE_DO_NOT_EXISTS(100202, Response.Status.BAD_REQUEST, "course do not exist"),
    PUT_SECTION_NO_SUCH_SECTION(100301, Response.Status.BAD_REQUEST, "no such section"),
    PUT_SECTION_NO_SUCH_CHAPTER(100302, Response.Status.BAD_REQUEST, "no such chapter"),
    DELETE_SECTION_COURSE_DO_NOT_EXISTS(100401, Response.Status.BAD_REQUEST, "section do not exist"),

    /*static file error*/
    POST_STATIC_FILE_FAILED_TO_STORE(110201, Response.Status.BAD_REQUEST, "failed to store file in server"),
    POST_STATIC_FILE_FAILED_TO_INSERT_DB(110202, Response.Status.BAD_REQUEST, "failed to insert mate data to database"),
    POST_STATIC_FILE_UNACCEPTABLE_FILE_TYPE(110203, Response.Status.BAD_REQUEST, "failed to upload file: file type is unacceptable"),
    GET_STATIC_FILE_FAILED_TO_DOWNLOAD_FILE(110101, Response.Status.BAD_REQUEST, "failed to find given identifier"),
    DELETE_STATIC_FILE_NO_SUCH_FILE(110401, Response.Status.BAD_REQUEST, "failed to find file"),
    DELETE_STATIC_FILE_FAILED_DELETE(110402, Response.Status.INTERNAL_SERVER_ERROR, "failed to delete file"),

    /* section file error */
    PUT_SECTION_FILE_NO_SUCH_COURSE(120301, Response.Status.BAD_REQUEST, "no such course"),
    PUT_SECTION_FILE_NO_SUCH_SECTION(120302, Response.Status.BAD_REQUEST, "no such section"),
    PUT_SECTION_FILE_NO_SUCH_FILE(120303, Response.Status.BAD_REQUEST, "no such file"),
    DELETE_SECTION_FILE_NO_SUCH_COURSE(120401, Response.Status.BAD_REQUEST, "no such course"),
    DELETE_SECTION_FILE_NO_SUCH_FILE(120402, Response.Status.BAD_REQUEST, "no such file"),

    /* exam error*/
    GET_EXAM_STUDENT_NOT_ALLOW(130102, Response.Status.BAD_REQUEST, "this student didnot apply to this course"),
    GET_EXAM_COURSE_DO_NOT_EXISTS(130103, Response.Status.BAD_REQUEST, "course do not exist"),
    POST_EXAM_COURSE_DO_NOT_EXISTS(130301, Response.Status.BAD_REQUEST, "no such course"),
    POST_EXAM_CHAPTER_DO_NOT_EXISTS(130302, Response.Status.BAD_REQUEST, "no such chapter"),
    POST_EXAM_ID_WRONG(130303,Response.Status.BAD_REQUEST,"examId and path examId do not equal"),

    /*experiment error*/
    POST_EXPERIMENT_NO_SUCH_COURSE(140201, Response.Status.BAD_REQUEST, "no such course"),
    POST_EXPERIMENT_FILE_NO_SUCH_EXPERIMENT(140202, Response.Status.BAD_REQUEST, "no such experiment"),
    POST_EXPERIMENT_FILE_NO_SUCH_FILE(140203, Response.Status.BAD_REQUEST, "no such experiment file"),
    ;


    private final int code;
    private final Response.Status status;
    private final String message;

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

package buptspirit.spm.rest.exception;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    private Logger logger;

    @Override
    public Response toResponse(Throwable t) {
        logger.warn("unhandled exception occurred, unknown error message sent", t);
        // shadow the unhandled error
        return ServiceError.UNKNOWN.toResponse();
    }
}

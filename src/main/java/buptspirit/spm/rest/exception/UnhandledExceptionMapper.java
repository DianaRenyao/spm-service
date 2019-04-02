package buptspirit.spm.rest.exception;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    private Logger logger;

    @Inject
    private ServiceExceptionMapper serviceExceptionMapper;

    @Override
    public Response toResponse(WebApplicationException e) {
        Response.Status status = Response.Status.fromStatusCode(e.getResponse().getStatus());
        return serviceExceptionMapper.toResponse(
                new ServiceException(status, e.getMessage())
        );
    }

    void log(WebApplicationException e) {
        logger.info("unhandled exception occurred", e);
    }
}

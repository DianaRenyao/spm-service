package buptspirit.spm.rest.exception;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<Throwable> {

    private static final ServiceException HIDE_WITH = new ServiceException(
            Response.Status.INTERNAL_SERVER_ERROR,
            "server internal error occurs, please contact developer");
    @Inject
    private Logger logger;
    @Inject
    private ServiceExceptionMapper serviceExceptionMapper;

    @Override
    public Response toResponse(Throwable throwable) {
        log(throwable);
        return serviceExceptionMapper.toResponse(HIDE_WITH);
    }

    public Response toResponseWith(Throwable throwable, BuilderOperation operation) {
        log(throwable);
        return serviceExceptionMapper.toResponseWith(HIDE_WITH, operation);
    }

    private void log(Throwable t) {
        logger.error("unhandled exception mapped by exception manager", t);
    }
}

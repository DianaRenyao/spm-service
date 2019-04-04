package buptspirit.spm.rest.exception;

import buptspirit.spm.rest.message.ErrorMessage;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnhandledWebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    private Logger logger;

    @Override
    public Response toResponse(WebApplicationException e) {
        logger.debug("unhandled web application exception occurred", e);
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(ServiceError.RAW_HTTP_ERROR.getCode());
        errorMessage.setStatus(e.getResponse().getStatus());
        errorMessage.setMessage(e.getMessage());
        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

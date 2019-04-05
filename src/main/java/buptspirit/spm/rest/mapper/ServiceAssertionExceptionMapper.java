package buptspirit.spm.rest.mapper;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.message.ErrorMessage;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceAssertionExceptionMapper implements ExceptionMapper<ServiceAssertionException> {

    @Inject
    private Logger logger;

    @Override
    public Response toResponse(ServiceAssertionException e) {
        logger.error("assertion exception occurred", e);
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(ServiceError.ASSERTION.getCode());
        errorMessage.setStatus(ServiceError.ASSERTION.getStatus().getStatusCode());
        errorMessage.setMessage(e.getMessage());
        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

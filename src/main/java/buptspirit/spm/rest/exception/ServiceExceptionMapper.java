package buptspirit.spm.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        return Response.status(e.getErrorMessage().getStatus())
                .type(MediaType.APPLICATION_JSON).entity(e.getErrorMessage()).build();
    }
}

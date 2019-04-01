package buptspirit.spm.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        return Response.status(e.getFullMessage().getStatus()).entity(e.getFullMessage()).build();
    }

    public Response toResponseWith(ServiceException e, BuilderOperation operation) {
        Response.ResponseBuilder builder = Response
                .status(e.getFullMessage().getStatus())
                .entity(e.getFullMessage());
        operation.execute(builder);
        return builder.build();
    }
}

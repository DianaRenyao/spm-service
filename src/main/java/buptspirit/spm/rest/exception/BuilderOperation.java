package buptspirit.spm.rest.exception;

import javax.ws.rs.core.Response;

@FunctionalInterface
public interface BuilderOperation {
    void execute(Response.ResponseBuilder builder);
}

package buptspirit.spm.rest.resource;

import buptspirit.spm.exception.ServiceError;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("meta")
public class MetaResource {

    @GET
    @Path("errors")
    @Produces(MediaType.TEXT_PLAIN)
    public String getErrors() {
        StringBuilder builder = new StringBuilder();
        builder.append("enum ErrorCode {\n");
        for (ServiceError error : ServiceError.values()) {
            builder.append("    ");
            builder.append(error.name());
            builder.append(" = ");
            builder.append(error.getCode());
            builder.append(",\n");
        }
        builder.append("}\n");
        return builder.toString();
    }

    @GET
    @Path("is-working")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getWorkingStatus() {
        return true;
    }
}

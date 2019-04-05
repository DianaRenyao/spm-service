package buptspirit.spm.exception;

import javax.ws.rs.core.Response;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 4372068319488842734L;

    private static final Response.Status DEFAULT_STATUS = Response.Status.BAD_REQUEST;

    private ServiceError serviceError;

    // can only be created from ServiceError
    ServiceException(ServiceError serviceError) {
        super(serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }
}



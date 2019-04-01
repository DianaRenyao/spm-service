package buptspirit.spm.rest.filter;

import buptspirit.spm.rest.exception.ServiceException;
import buptspirit.spm.rest.exception.ServiceExceptionMapper;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    private Logger logger;

    @Inject
    private ServiceExceptionMapper serviceExceptionMapper;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        boolean havePermission;
        if (methodRoles.isEmpty()) {
            havePermission = checkPermissions(containerRequestContext, classRoles);
        } else {
            havePermission = checkPermissions(containerRequestContext, methodRoles);
        }
        if (!havePermission) {
            logger.info("unauthorized access");
            ServiceException forbidden = new ServiceException(
                    Response.Status.FORBIDDEN,
                    "operation on the resource is not accessible from the user role or not logged in");
            Response rejectWith = serviceExceptionMapper.toResponseWith(
                    forbidden,
                    builder -> builder.type(MediaType.APPLICATION_JSON));
            containerRequestContext.abortWith(rejectWith);
        }
    }

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private boolean checkPermissions(ContainerRequestContext containerRequestContext, List<Role> allowedRoles) {
        return allowedRoles.stream().anyMatch(
                r -> containerRequestContext
                        .getSecurityContext()
                        .isUserInRole(r.getName()));
    }
}

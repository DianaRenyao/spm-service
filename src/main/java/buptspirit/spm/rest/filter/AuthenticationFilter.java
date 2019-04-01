package buptspirit.spm.rest.filter;

import buptspirit.spm.rest.token.TokenManager;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Date;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Inject
    private TokenManager tokenManager;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        DecodedJWT jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            jwt = tokenManager.verifiedOrNull(token);
            if (jwt != null) {
                Date expire = jwt.getExpiresAt();
                if (expire.before(new Date())) {
                    // expired
                    jwt = null;
                }
            }
        }

        if (jwt != null) {
            // authenticated
            String username = jwt.getSubject();
            String role = jwt.getClaim("role").asString();
            requestContext.setSecurityContext(new SecurityContextImpl(
                    username,
                    role,
                    false,
                    null));
        }
    }
}

class SecurityContextImpl implements SecurityContext {
    private Principal principal;
    private String role;
    private boolean secure;
    private String authenticationScheme;

    SecurityContextImpl(String username, String role, boolean secure, String authenticationScheme) {
        this.principal = new PrincipalImpl(username);
        this.role = role;
        this.secure = secure;
        this.authenticationScheme = authenticationScheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.role.equals(role);
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return this.authenticationScheme;
    }
}

class PrincipalImpl implements Principal {
    private String username;

    PrincipalImpl(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}

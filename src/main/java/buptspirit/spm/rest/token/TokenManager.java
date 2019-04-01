package buptspirit.spm.rest.token;

import buptspirit.spm.persistence.entity.TokenSecret;
import buptspirit.spm.persistence.facade.TokenSecretFacade;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

import static buptspirit.spm.persistence.JpaUtility.transactional;

@Singleton
public class TokenManager {
    private static final String ISSUER = "bupt-spirit";

    private static String secret;

    @Inject
    private Logger logger;

    @Inject
    private TokenSecretFacade tokenSecretFacade;

    @PostConstruct
    public void postConstruct() {
        secret = transactional(
                em -> {
                    TokenSecret entity = tokenSecretFacade.find(em, 0);
                    if (entity == null) {
                        entity = new TokenSecret();
                        entity.setId(0);
                        entity.setSecret(generateSecret());
                        tokenSecretFacade.create(em, entity);
                    }
                    return entity.getSecret();
                },
                "failed to find token secret"
        );
        logger.info("token secret created or fetched {}", secret);
    }

    private String generateSecret() {
        return RandomStringUtils.randomAlphanumeric(64);
    }

    public String issue(String username, String role, long expireTime) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            long current = System.currentTimeMillis();
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(username)
                    .withIssuedAt(new Date(current))
                    .withExpiresAt(new Date(current + expireTime))
                    .withClaim("role", role)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("invalid JWT configuration");
        }
    }

    public DecodedJWT verifiedOrNull(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}

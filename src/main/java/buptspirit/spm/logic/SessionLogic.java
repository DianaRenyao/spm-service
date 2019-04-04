package buptspirit.spm.logic;

import buptspirit.spm.message.LoginMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.message.UserInfoMessage;
import buptspirit.spm.persistence.entity.TokenSecretEntity;
import buptspirit.spm.persistence.facade.TokenSecretFacade;
import buptspirit.spm.rest.exception.ServiceError;
import buptspirit.spm.rest.exception.ServiceException;
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
public class SessionLogic {
    private static final String ISSUER = "bupt-spirit";
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 1 day

    private static String secret;

    @Inject
    private Logger logger;

    @Inject
    private TokenSecretFacade tokenSecretFacade;

    @Inject
    private UserLogic userLogic;

    @Inject

    @PostConstruct
    public void postConstruct() {
        logger.info("successfully constructed");
        secret = transactional(
                em -> {
                    TokenSecretEntity entity = tokenSecretFacade.find(em, 0);
                    if (entity == null) {
                        entity = new TokenSecretEntity();
                        entity.setTokenSecretId(0);
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

    private SessionMessage issue(UserInfoMessage userInfo, long expireTime) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            long current = System.currentTimeMillis();
            Date issuedAt = new Date(current);
            Date expiresAt = new Date(current + expireTime);
            SessionMessage message = new SessionMessage();
            message.setAuthenticated(true);
            message.setIssuedAt(issuedAt);
            message.setExpiresAt(expiresAt);
            message.setUserInfo(userInfo);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userInfo.getUsername())
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expiresAt)
                    .withClaim("id", userInfo.getId())
                    .withClaim("role", userInfo.getRole())
                    .withClaim("timeCreated", userInfo.getTimeCreated())
                    .withClaim("realName", userInfo.getRealName())
                    .withClaim("email", userInfo.getEmail())
                    .withClaim("phone", userInfo.getPhone())
                    .sign(algorithm);
            message.setToken(token);
            return message;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("invalid JWT configuration");
        }
    }

    // return null if invalid
    private SessionMessage verifiedOrNull(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT decoded = verifier.verify(token);
            if (decoded != null) {
                Date expire = decoded.getExpiresAt();
                if (expire.before(new Date())) {
                    // expired
                    decoded = null;
                }
            }
            if (decoded != null) {
                SessionMessage message = new SessionMessage();
                message.setAuthenticated(true);
                message.setToken(token);
                message.setIssuedAt(decoded.getIssuedAt());
                message.setExpiresAt(decoded.getExpiresAt());
                UserInfoMessage userInfoMessage = new UserInfoMessage();
                userInfoMessage.setId(decoded.getClaim("id").asInt());
                userInfoMessage.setUsername(decoded.getSubject());
                userInfoMessage.setTimeCreated(decoded.getClaim("timeCreated").asDate());
                userInfoMessage.setRole(decoded.getClaim("role").asString());
                userInfoMessage.setRealName(decoded.getClaim("realName").asString());
                userInfoMessage.setEmail(decoded.getClaim("email").asString());
                userInfoMessage.setPhone(decoded.getClaim("phone").asString());
                message.setUserInfo(userInfoMessage);
                return message;
            } else {
                return SessionMessage.Unauthenticated();
            }
        } catch (JWTVerificationException exception) {
            return SessionMessage.Unauthenticated();
        }
    }

    public SessionMessage createSession(LoginMessage login) throws ServiceException {
        UserInfoMessage userInfo = userLogic.verify(login);
        if (userInfo != null) {
            return issue(userInfo, EXPIRE_TIME);
        } else {
            throw ServiceError.INVALID_USERNAME_OR_PASSWORD.toException();
        }
    }

    // return null if token is invalid
    public SessionMessage getSessionFromToken(String token) {
        return verifiedOrNull(token);
    }
}

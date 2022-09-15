package it.biro.biro_sec.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private String expirationTime;
    @Value("${jwt.refresh.expiration.time}")
    private String refreshTokenExpirationTime;

    @Value("${jwt.issuer}")
    private String issuer;
    private final String USERNAME_CLAIM = "username";

    public Map<String, String> generateTokens(String login, boolean useRefreshToken) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("token",
                generateToken(login, Optional.of(Integer.valueOf(expirationTime))));
        if (useRefreshToken)
            tokens.put("refreshToken",
                    generateToken(login, Optional.of(Integer.valueOf(refreshTokenExpirationTime))));

        return tokens;
    }

    private String generateToken(String login, Optional<Integer> expiration) throws IllegalArgumentException, JWTCreationException {
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        JWTCreator.Builder tokenBuilder = JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withNotBefore(now)
                .withIssuer(issuer)
                .withSubject("User Details")
                .withClaim(USERNAME_CLAIM, login);

        if (expiration.isPresent()) {
            c.add(Calendar.MINUTE, expiration.get());
            Date expirationDate = c.getTime();
            tokenBuilder.withExpiresAt(expirationDate);
        }
        return tokenBuilder.sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .withSubject("User Details")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(USERNAME_CLAIM).asString();
    }
}

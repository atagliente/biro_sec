package it.biro.biro_sec.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.util.*;

@Component
public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.cipher.key.path}")
    private String cipherKeyPath;
    @Value("${jwt.expiration.time}")
    private String expirationTime;
    @Value("${jwt.refresh.expiration.time}")
    private String refreshTokenExpirationTime;
    @Value("${jwt.issuer}")
    private String issuer;
    @Autowired
    private TokenCipher tokenCipher;

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
        return cipherToken(tokenBuilder.sign(Algorithm.HMAC256(secret)));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        token = decipherToken(DatatypeConverter.parseString(token));
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .withSubject("User Details")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(USERNAME_CLAIM).asString();
    }

    private String cipherToken(String token) {
        try {
            return tokenCipher.cipherToken(token, CleartextKeysetHandle.read(
                    JsonKeysetReader.withFile(new File(cipherKeyPath))));
        } catch (Exception e) {
            logger.error("Cipher key is NOT valid");
            throw new RuntimeException(e);
        }
    }

    private String decipherToken(String token) {
        try {
            return tokenCipher.decipherToken(token, CleartextKeysetHandle.read(
                    JsonKeysetReader.withFile(new File(cipherKeyPath))));
        } catch (Exception e) {
            logger.error("Cipher key is NOT valid");
            throw new RuntimeException(e);
        }
    }

}

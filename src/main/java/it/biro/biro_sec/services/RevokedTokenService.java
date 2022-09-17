package it.biro.biro_sec.services;

import it.biro.biro_sec.entities.RevokedToken;
import it.biro.biro_sec.repositories.RevokedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevokedTokenService {

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;


    public RevokedToken save(RevokedToken revokedToken) {
        return revokedTokenRepository.save(revokedToken);
    }

    public Optional<RevokedToken> findByJWTtokenDigest(String JWTtokenDigest) {
        return revokedTokenRepository.findByJWTtokenDigest(JWTtokenDigest);
    }

    public void revoke(String token) {
        if (token != null && !token.trim().isEmpty()) {
            if (findByJWTtokenDigest(token).isEmpty()) {
                RevokedToken revokedToken = new RevokedToken(token);
                revokedTokenRepository.save(revokedToken);
            }
        }

    }

}

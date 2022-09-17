package it.biro.biro_sec.repositories;

import it.biro.biro_sec.entities.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {

    Optional<RevokedToken> findByJWTtokenDigest(String JWTtokenDigest);
}

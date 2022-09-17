package it.biro.biro_sec.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RevokedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "jwt_token_digest")
    private String JWTtokenDigest;
    @Column(name = "revocation_date")
    private Date revocationDate;

    public RevokedToken(String JWTtokenDigest) {
        this.JWTtokenDigest = JWTtokenDigest;
    }

    public RevokedToken() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJWTtokenDigest() {
        return JWTtokenDigest;
    }

    public void setJWTtokenDigest(String JWTtokenDigest) {
        this.JWTtokenDigest = JWTtokenDigest;
    }

    public Date getRevocationDate() {
        return revocationDate;
    }

    public void setRevocationDate(Date revocationDate) {
        this.revocationDate = revocationDate;
    }
}

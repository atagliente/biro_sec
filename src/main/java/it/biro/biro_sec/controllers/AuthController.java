package it.biro.biro_sec.controllers;


import it.biro.biro_sec.entities.Account;
import it.biro.biro_sec.services.AccountService;
import it.biro.biro_sec.services.RevokedTokenService;
import it.biro.biro_sec.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RevokedTokenService revokedTokenService;

    @PostMapping("/register")
    public Map<String, String> registerHandler(@RequestBody Account account){
        String encodedPass = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPass);
        account = accountService.save(account);

        return jwtUtil.generateTokens(account.getUsername(), true);
    }

    @PostMapping("/login")
    public Map<String, String> loginHandler(@RequestBody LoginCredentials body){
        try {
            // Creating the Authentication Token which will contain the credentials for authenticating
            // This token is used as input to the authentication process
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            authenticationManager.authenticate(authInputToken);

            return jwtUtil.generateTokens(body.getUsername(), true);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutHandler(HttpServletRequest request) {
        // token verify already done
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        revokedTokenService.revoke(token);
        return ResponseEntity.ok().build();
    }

}

class LoginCredentials {
    private String username;
    private String password;

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginCredentials() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

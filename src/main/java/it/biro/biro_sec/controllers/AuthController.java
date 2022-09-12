package it.biro.biro_sec.controllers;


import it.biro.biro_sec.services.AccountService;
import it.biro.biro_sec.entities.Account;
import it.biro.biro_sec.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody Account account){
        // Encoding Password using Bcrypt
        String encodedPass = passwordEncoder.encode(account.getPassword());

        // Setting the encoded password
        account.setPassword(encodedPass);

        // Persisting the User Entity to H2 Database
        account = accountService.save(account);

        // Generating JWT
        String token = jwtUtil.generateToken(account.getUsername());

        // Responding with JWT
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            // Creating the Authentication Token which will contain the credentials for authenticating
            // This token is used as input to the authentication process
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            // Authenticating the Login Credentials
            authenticationManager.authenticate(authInputToken);

            // If this point is reached it means Authentication was successful
            // Generate the JWT
            String token = jwtUtil.generateToken(body.getUsername());

            // Respond with the JWT
            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            // Auhentication Failed
            throw new RuntimeException("Invalid Login Credentials");
        }
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

package it.biro.biro_sec;

import com.nimbusds.jose.shaded.json.JSONObject;
import it.biro.biro_sec.filters.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableJpaRepositories
public class SecurityConfig {

    @Autowired
    private JWTFilter JWTfilter;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeHttpRequests() // Authorizing incoming requests
                .antMatchers("/api/auth/**").permitAll() // Allows auth requests to be made without authentication of any sort
                .antMatchers("/api/user/**").hasRole("USER") // Allows only users with the "USER" role to make requests to the user routes
                .and()
                .userDetailsService(userDetailsService) // Setting the user details service to the custom implementation
                .exceptionHandling()
                .authenticationEntryPoint(
                        // Rejecting request as unauthorized when entry point is reached
                        // If this point is reached it means that the current request requires authentication
                        // and no JWT token was found attached to the Authorization header of the current request.
                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Setting Session to be stateless

        http.addFilterBefore(JWTfilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



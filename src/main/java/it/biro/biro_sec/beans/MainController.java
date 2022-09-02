package it.biro.biro_sec.beans;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/")
    public String home(){
        return "Main Home!";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping("/user")
    public String homeUser(){
        return "Hello World USER!";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/admin")
    public String homeAdmin(){
        return "Hello World ADMIN!";
    }

}
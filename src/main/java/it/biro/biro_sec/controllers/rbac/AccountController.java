package it.biro.biro_sec.controllers.rbac;

import it.biro.biro_sec.services.AccountService;
import it.biro.biro_sec.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/info")
    public Account getUserDetails(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountService.findByUsername(username).orElse(null);
    }

}

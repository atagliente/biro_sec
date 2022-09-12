package it.biro.biro_sec;

import it.biro.biro_sec.entities.Account;
import it.biro.biro_sec.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch User from the DB
        Optional<Account> userRes = accountRepository.findByUsername(username);
        // No user found
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with username = " + username);
        // Return a User Details object using the fetched User information
        Account account = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                account.getPassword(),
                account.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList())); // Sets the role of the found user
    }
}

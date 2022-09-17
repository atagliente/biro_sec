package it.biro.biro_sec.services;

import it.biro.biro_sec.entities.Account;
import it.biro.biro_sec.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(final Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByUsername(final String username) {
        return accountRepository.findByUsername(username);
    }

    public void delete(final Long id) {
        accountRepository.deleteById(id);
    }

    public Account save(final Account account) {
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch User from the DB
        Optional<Account> userRes = accountRepository.findByUsername(username);
        // No user found
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with username = " + username);
        // Return a User Details object using the fetched User information
        Account account = userRes.get();
        Function<Account, UserDetails> userDetails = a -> new org.springframework.security.core.userdetails.User(
                a.getUsername(),
                a.getPassword(),
                a.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));

        return userDetails.apply(account);
    }
}

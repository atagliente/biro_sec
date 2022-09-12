package it.biro.biro_sec.services;

import it.biro.biro_sec.repositories.AccountRepository;
import it.biro.biro_sec.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

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

}

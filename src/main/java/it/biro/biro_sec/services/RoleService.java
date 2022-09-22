package it.biro.biro_sec.services;

import it.biro.biro_sec.entities.Account;
import it.biro.biro_sec.repositories.RoleRepository;
import it.biro.biro_sec.repositories.AccountRepository;
import it.biro.biro_sec.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void delete(final String name) {
        roleRepository.deleteByName(name);
    }

    public void save(final Role role) {
        roleRepository.save(role);
    }

    public void assignUserRole(final Long userId, final Integer roleId){
        Optional<Account> user  = accountRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            if (user.isPresent()) {
                Set<Role> userRoles = user.get().getRoles();
                userRoles.add(role.get());
                user.get().setRoles(userRoles);
                accountRepository.save(user.get());
            } else {
                logger.error("USER {} ISN'T PRESENT!!!", userId);
            }
        } else {
            logger.error("ROLE {} ISN'T PRESENT!!!", roleId);
        }
    }
    
}

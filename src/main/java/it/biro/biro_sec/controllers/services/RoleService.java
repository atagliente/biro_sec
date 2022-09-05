package it.biro.biro_sec.controllers.services;

import it.biro.biro_sec.controllers.repositories.RoleRepository;
import it.biro.biro_sec.controllers.repositories.UserRepository;
import it.biro.biro_sec.entities.Role;
import it.biro.biro_sec.entities.User;
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
    private UserRepository userRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(final int id) {
        return roleRepository.findById(id);
    }

    public void delete(final int id) {
        roleRepository.deleteById(id);
    }

    public void save(final Role role) {
        roleRepository.save(role);
    }

    public void assignUserRole(final Integer userId, final Integer roleId){
        Optional<User> user  = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            if (user.isPresent()) {
                Set<Role> userRoles = user.get().getRoles();
                userRoles.add(role.get());
                user.get().setRoles(userRoles);
                userRepository.save(user.get());
            } else {
                logger.error("USER {} ISN'T PRESENT!!!", userId);
            }
        } else {
            logger.error("ROLE {} ISN'T PRESENT!!!", roleId);
        }
    }

    public void unassignUserRole(final Integer userId, final Integer roleId){
        Optional<User> user  = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().getRoles().removeIf(x -> x.getId()==roleId);
            userRepository.save(user.get());
        } else {
            logger.error("USER {} ISN'T PRESENT!!!", userId);
        }
    }

    public Set<Role> getUserRoles(final User user){
        return user.getRoles();
    }

    public Set<Role> getUserNotRoles(final User user){
        return roleRepository.getUserNotRoles(user.getId());
    }

}

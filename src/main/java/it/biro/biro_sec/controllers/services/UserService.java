package it.biro.biro_sec.controllers.services;

import it.biro.biro_sec.controllers.repositories.UserRepository;
import it.biro.biro_sec.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(final int id) {
        return userRepository.findById(id);
    }

    public void delete(final int id) {
        userRepository.deleteById(id);
    }

    public void save(final User role) {
        userRepository.save(role);
    }

}

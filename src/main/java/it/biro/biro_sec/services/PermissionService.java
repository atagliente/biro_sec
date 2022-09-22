package it.biro.biro_sec.services;

import it.biro.biro_sec.entities.Permission;
import it.biro.biro_sec.repositories.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PermissionService {

    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> getPermissions () {
        return permissionRepository.findAll();
    }

    public void delete(final String name) {
        permissionRepository.deleteByName(name);
    }

    public void save(final Permission role) {
        permissionRepository.save(role);
    }

}

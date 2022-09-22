package it.biro.biro_sec.controllers.rbac;

import it.biro.biro_sec.entities.Permission;
import it.biro.biro_sec.services.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/permission")
public class PermissionController {

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @PutMapping("/")
    public @ResponseBody List<Permission> create(@RequestBody Permission permission){
        permissionService.save(permission);
        return permissionService.getPermissions();
    }

    @GetMapping("/all")
    public @ResponseBody List<Permission> all(){
        return permissionService.getPermissions();
    }

    @DeleteMapping("/{name}")
    public @ResponseBody List<Permission> deletebyName(@PathVariable String name){
        permissionService.delete(name);
        return permissionService.getPermissions();
    }

}

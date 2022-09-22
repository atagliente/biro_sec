package it.biro.biro_sec.controllers.rbac;
import it.biro.biro_sec.entities.Role;
import it.biro.biro_sec.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @PostMapping ("/create")
    public @ResponseBody List<Role> create(@RequestBody Role role){
        roleService.save(role);
        return roleService.getRoles();
    }

    @GetMapping("/all")
    public @ResponseBody List<Role> all(){
        return roleService.getRoles();
    }

    @DeleteMapping("/{name}")
    public @ResponseBody List<Role> deletebyName(@PathVariable String name){
        roleService.delete(name);
        return roleService.getRoles();
    }

}

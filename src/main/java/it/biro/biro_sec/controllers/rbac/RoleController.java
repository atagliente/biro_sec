package it.biro.biro_sec.controllers.rbac;
import it.biro.biro_sec.entities.Role;
import it.biro.biro_sec.services.RoleService;
import it.biro.biro_sec.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public @ResponseBody List<String> create(@RequestBody Role role){
        roleService.save(role);
        return roleService.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public @ResponseBody List<String> all(){
        return roleService.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public @ResponseBody List<String> deletebyName(@RequestBody Role role){
        roleService.delete(role.getName());
        return roleService.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

}

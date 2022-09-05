package it.biro.biro_sec.controllers.rbac;

import it.biro.biro_sec.controllers.services.RoleService;
import it.biro.biro_sec.controllers.services.UserService;
import it.biro.biro_sec.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @GetMapping("/security/user/Edit/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user);
            model.addAttribute("userRoles", roleService.getUserRoles(user.get()));
            model.addAttribute("userNotRoles", roleService.getUserNotRoles(user.get()));
        } else {
            logger.error("USER {} ISN'T PRESENT!!!", id);
        }
        return "/userEdit";
    }

}

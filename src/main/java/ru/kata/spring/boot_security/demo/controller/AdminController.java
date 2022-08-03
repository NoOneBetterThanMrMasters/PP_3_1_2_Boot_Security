package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.ArrayList;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;

        this.roleService = roleService;
    }


    @GetMapping("/")
    public String getAdminPage(){
        return "admin";
    }

    @RequestMapping(value = "/admin_b", method = RequestMethod.GET)
    public String getAdmin_bPage(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userIn", user);
        model.addAttribute("users", userService.getListUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("newRole", new ArrayList<Role>());

        return "admin_panel";
    }
    @RequestMapping(value = "/admin_b", method = RequestMethod.POST)
    public String postNewUser(@ModelAttribute("newUser") User newUser, @RequestParam(value = "newRole") String[] roles){
        newUser.setRoles(roleService.getRole(roles));
        userService.add(newUser);
        return "redirect:/admin_b";
    }

    @RequestMapping(value = "/admin_b/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("userEdit") User userEdit, @RequestParam(value = "editRole") String[] roles){
        userEdit.setRoles(roleService.getRole(roles));
        userService.add(userEdit);
        return "redirect:/admin_b";
    }
    @RequestMapping(value = "/admin_b/{id}", method = RequestMethod.GET)
    public String updateUserById (@PathVariable ("id") int id, Model model){
        User user = userService.getById(id);
        model.addAttribute("userEdit", user);
        return "redirect:/admin_b";
    }
    @PostMapping("/user-update")
    public String updateUser(@RequestParam(value = "role") String[] roles, @ModelAttribute("user") User user, int id){
        roleService.getRole(roles);
        userService.update(user, id);
        return "redirect:/admin_b";
    }
    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin_b";
    }
}

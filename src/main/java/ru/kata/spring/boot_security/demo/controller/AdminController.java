package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("users", userService.getListUsers());
        return "users-list";
    }
    @GetMapping("/user-create")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "user-create";
    }
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles){
        roleService.getRole(roles);
        userService.add(user);
        return "redirect:/users";
    }
    @GetMapping("/user-update/{id}")
    public String updateUserById (@PathVariable("id") int id, Model model){
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user-update";
    }
    @PostMapping("/user-update")
    public String updateUser(@RequestParam(value = "role") String[] roles, @ModelAttribute("user") User user, int id){
        roleService.getRole(roles);
        userService.update(user, id);
        return "redirect:/users";
    }
    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/users";
    }
}

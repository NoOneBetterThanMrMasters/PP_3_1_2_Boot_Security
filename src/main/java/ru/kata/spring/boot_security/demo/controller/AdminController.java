package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final UserDaoImpl userDao;
    private final RoleDaoImpl roleDao;

    @Autowired
    public AdminController(UserServiceImpl userService, UserDaoImpl userDao, RoleDaoImpl roleDao) {
        this.userService = userService;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }
    public Set<Role> getRole(String[] roles){
        Set<Role> userRole = new HashSet<>();
        for (String roleName : roles){
            userRole.add((Role) roleDao.findByIdRoles(roleName));
        }

        return userRole;
    }

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }
    @GetMapping("/admin/users")
    public String getUsers(Model model){
        model.addAttribute("users", userService.getListUsers());
        return "users-list";
    }
    @GetMapping("/admin/user-create")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "user-create";
    }
    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles){
        user.setRoles(getRole(roles));
        userService.add(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/user-update/{id}")
    public String updateUserById (@PathVariable("id") int id, Model model){
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "user-update";
    }
    @PostMapping("admin/user-update")
    public String updateUser(@RequestParam(value = "role") String[] roles, @ModelAttribute("user") User user, int id){
        user.setRoles(getRole(roles));
        userService.update(user, id);
        return "redirect:/admin/users";
    }
    @GetMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin/users";
    }
}

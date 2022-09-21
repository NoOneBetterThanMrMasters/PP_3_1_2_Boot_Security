package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;


    @Autowired
    public AdminRestController(UserService userService, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/adminpage")
    public ResponseEntity<List<User>> userList() {
        final List<User> users = userService.getListUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/adminpage/new")
    public List<User> addUser(@RequestBody User user) {
        userService.add(user);
        return userService.getListUsers();
    }

    @PutMapping("/adminpage/edit")
    public ResponseEntity<?> update(@RequestBody User user, int id) {
        userService.update(user, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/adminpage/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

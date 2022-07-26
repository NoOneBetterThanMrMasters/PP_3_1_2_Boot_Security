package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> getListUsers();

    void add(User user);

    void delete(int id);

    void update(User user, int id);

    User getById(int id);
}
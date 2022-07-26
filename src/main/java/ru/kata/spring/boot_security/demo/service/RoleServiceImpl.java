package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;
    @Autowired
    public void setUserDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Override
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public Set<Role> findByIdRoles(String roleName) {
        return (Set<Role>) roleDao.findByIdRoles(roleName);
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}

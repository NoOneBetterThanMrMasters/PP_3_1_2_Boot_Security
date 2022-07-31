package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        return (Set<Role>) entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public Role findById(Integer id) {
        return entityManager.find(Role.class,id);
    }

    @Override
    public Set<Role> findByIdRoles(String roleName) {
      return (Set<Role>)  entityManager.createQuery("SELECT role FROM Role role where role.roleName = :roleName",
              Role.class).setParameter("role", roleName).getSingleResult();
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

}

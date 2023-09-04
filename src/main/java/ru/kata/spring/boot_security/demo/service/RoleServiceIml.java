package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
@Service
@RequiredArgsConstructor
public class RoleServiceIml implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Iterable<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}

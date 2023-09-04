package ru.kata.spring.boot_security.demo.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserMapper {
    public static User toUser(UserDTO user, PasswordEncoder encoder, Iterable<Role> allRoles) {
        return new User(
                user.getId(),
                user.getUsername(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                encoder.encode(user.getPassword()),
                toRoles(allRoles, user.getRoles())
        );
    }

    private static Collection<Role> toRoles(Iterable<Role> allRoles, String[] userRoles) {
        return StreamSupport.stream(allRoles.spliterator(), false)
                .filter(role -> Arrays.asList(userRoles).contains(role.getRole()))
                .collect(Collectors.toList());
    }
}
package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername (String name);
}

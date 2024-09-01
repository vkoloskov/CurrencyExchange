package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.entity.User;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();
    User findById(Long id);
    User save(User user);

    @SneakyThrows
    Optional<User> findByEmailAndPassword(String email, String password);

    boolean update(User user);

}

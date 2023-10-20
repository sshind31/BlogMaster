package com.cdac.blog.service;

import com.cdac.blog.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User theUser);
    List<User> findAllUsers();
    Optional<User> findByEmail(String email);
}

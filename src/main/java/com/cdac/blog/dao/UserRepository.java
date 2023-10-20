package com.cdac.blog.dao;

import com.cdac.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//dao is another name for repository  classes
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

package com.cdac.blog.dao;

import com.cdac.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//dao is another name for repository  classes
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String userRole);
}

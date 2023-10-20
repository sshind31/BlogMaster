package com.cdac.blog.service.implementation;

import com.cdac.blog.dao.RoleRepository;
import com.cdac.blog.dao.UserRepository;
import com.cdac.blog.entity.Role;
import com.cdac.blog.entity.User;
import com.cdac.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // inseted of @component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String AUTHOR_ROLE = "ROLE_AUTHOR";

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository, RoleRepository theRoleRepository, PasswordEncoder thePasswordEncoder) {
        userRepository = theUserRepository;
        roleRepository = theRoleRepository;
        passwordEncoder = thePasswordEncoder;
    }

    @Override
    public void saveUser(User theUser) {
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        Optional<Role> role = roleRepository.findByRole(AUTHOR_ROLE);
        if(role.isPresent()) {
            theUser.addRole(role.get());
            userRepository.save(theUser);
        } else {
            Role newRole = new Role();
            newRole.setRole(AUTHOR_ROLE);
            theUser.addRole(newRole);
            userRepository.save(theUser);
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

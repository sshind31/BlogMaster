package com.cdac.blog.controller;

import com.cdac.blog.entity.User;
import com.cdac.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/signup")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "user/register-user";
    }

    @PostMapping(value = "/signup")
    public String createNewUser(@Valid User user,
                                BindingResult bindingResult,
                                Model model) {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (!bindingResult.hasErrors()) {
            user.setActive(1);
            userService.saveUser(user);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
        }
        return "user/register-user";
    }
}

package com.cdac.blog.controller;

import com.cdac.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "user/access-denied";
    }

}

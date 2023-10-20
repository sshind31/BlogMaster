package com.cdac.blog.controller;

import com.cdac.blog.entity.Post;
import com.cdac.blog.entity.Tag;
import com.cdac.blog.entity.User;
import com.cdac.blog.service.PostService;
import com.cdac.blog.service.TagService;
import com.cdac.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final PostService postService;
    private final TagService tagService;
    private final UserService userService;

    @Autowired
    public HomeController(PostService thePostService, TagService theTagservice, UserService theUserService) {
        postService = thePostService;
        tagService = theTagservice;
        userService = theUserService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal,
                       @RequestParam(defaultValue = "1") String start,
                       @RequestParam(defaultValue = "10") String limit,// pagination
                       @RequestParam Optional<String> publishedStart,//filters
                       @RequestParam Optional<String> publishedEnd,//filters
                       @RequestParam Optional<String> order,//filters
                       @RequestParam Optional<String> search,//filters
                       @RequestParam Optional<List<Long>> tagId,
                       @RequestParam Optional<List<Long>> authorId) {
        List<Tag> tags = tagService.findAllTags();
        List<User> users = userService.findAllUsers();
        Optional<User> user = userService.findByEmail(principal.getName());
        Page<Post> postPage = postService.findFilteredPostHomePaged(start,limit,publishedStart,publishedEnd,order,search,tagId,authorId,user.get().getId());
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", Integer.parseInt(start)/10 + 1);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("start", Integer.parseInt(start));
        model.addAttribute("limit", Integer.parseInt(limit));
        model.addAttribute("tags",tags);
        model.addAttribute("users",users);
        return "posts/list-posts";
    }
}

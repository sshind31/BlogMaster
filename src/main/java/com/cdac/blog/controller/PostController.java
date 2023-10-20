package com.cdac.blog.controller;

import com.cdac.blog.entity.*;
import com.cdac.blog.service.PostService;
import com.cdac.blog.service.TagService;
import com.cdac.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String listPosts(Model model,
                            @RequestParam(defaultValue = "1") String start,
                            @RequestParam(defaultValue = "10") String limit,
                            @RequestParam Optional<String> publishedStart,
                            @RequestParam Optional<String> publishedEnd,
                            @RequestParam Optional<String> order,
                            @RequestParam Optional<String> search,
                            @RequestParam Optional<List<Long>> tagId,
                            @RequestParam Optional<List<Long>> authorId) {
        List<Tag> tags = tagService.findAllTags();
        List<User> users = userService.findAllUsers();
        Page<Post> postPage = postService.findFilteredPostPaged(start,limit,publishedStart,publishedEnd,order,search,tagId,authorId);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", Integer.parseInt(start)/10 + 1);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("start", Integer.parseInt(start));
        model.addAttribute("limit", Integer.parseInt(limit));
        model.addAttribute("tags",tags);
        model.addAttribute("users",users);
        return "posts/list-posts";
    }
  //My code
    @GetMapping("/admin")
    public String admincontrol(Model model, Principal principal)
    {
    	Optional<User> user = userService.findByEmail(principal.getName());
    	model.addAttribute("temppost",postService.getAllPublishedPosts());
    	return "posts/admin_control";
    }

    @GetMapping("/{id}")
    public String getPostWithId(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            Comment commentNew = new Comment();
            model.addAttribute("commentNew", commentNew);
            return "posts/post-details";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/newpost")
    public String showFormForAdd(Model theModel, Principal principal) {
        Post thePost = new Post();
        Optional<User> user = userService.findByEmail(principal.getName());
        if(user.isPresent()) {
            thePost.setAuthor(user.get().getName());
        }
        theModel.addAttribute("post", thePost);
        return "posts/post-form";
    }

    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post thePost, Principal principal ) {
        if (principal!=null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if(user.isPresent()) {
                thePost.setUser(user.get());
            }
        }
        postService.savePost(thePost);
        return "redirect:/"+thePost.getId().toString();
    }

    @GetMapping("/{id}/editpost")
    public String editPost(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if(optionalPost.isPresent()) {
            if(isPrincipalOwnerOfPost(principal, optionalPost.get())) {
                Post post = optionalPost.get();
                model.addAttribute("post",post);
                return "posts/post-form";
            } else {
                return "redirect:/"+id;
            }
        } else {
            return "redirect:/"+id.toString();
        }
    }

    @GetMapping("/{id}/deletepost")
    public String deletePost(@PathVariable Long id, Principal principal) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if(optionalPost.isPresent()) {
            if( isPrincipalOwnerOfPost(principal, optionalPost.get())) {
                postService.deletePostById(id);
            } else {
                return "redirect:/"+id.toString();
            }
        }
        return "redirect:/";
    }
    
    @GetMapping("/{id}/adminDeletePost")
    public String adminDeletePost(@PathVariable Long id, Principal principal) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if(optionalPost.isPresent()) {          
                postService.deletePostById(id);
        }
        return "redirect:/admin";
    }

    @GetMapping("/{id}/publishpost")
    public String publishPost(@PathVariable Long id, Principal principal) {
        Optional<Post> optionalPost = postService.findPostById(id);
        if(optionalPost.isPresent()) {
            if( isPrincipalOwnerOfPost(principal, optionalPost.get())) {
                postService.updatePublishedStatus(id);
            } else {
                return "redirect:/"+id.toString();
            }
        }
        return "redirect:/";
    }

    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        List<Role> roles = post.getUser().getRoles();
        boolean admin = false;
        for (Role role:roles) {
            if (role.getRole().equals("ROLE_ADMIN")) {
                admin = true;
                break;
            }
        }
        return admin || principal != null && principal.getName().equals(post.getUser().getEmail());
    }


}

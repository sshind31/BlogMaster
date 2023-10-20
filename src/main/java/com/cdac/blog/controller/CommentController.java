package com.cdac.blog.controller;

import com.cdac.blog.entity.Comment;
import com.cdac.blog.entity.Post;
import com.cdac.blog.entity.Role;
import com.cdac.blog.entity.User;
import com.cdac.blog.service.CommentService;
import com.cdac.blog.service.PostService;
import com.cdac.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping("/{id}/addcomment")
    public String addComment(@ModelAttribute("commentNew") Comment theComment, @PathVariable Long id, Principal principal) {
        if (theComment.getComment() != null) {
            Optional<Post> optionalPost = postService.findPostById(id);
            if(optionalPost.isPresent()) {
                if(theComment.getId() != null) {
                    if(principal.getName()!=null ) {
                        theComment.setEmail(principal.getName());
                        Optional<User> user = userService.findByEmail(principal.getName());
                        if (user.isPresent()) {
                            theComment.setName(user.get().getName());
                        }
                    }
                    commentService.saveComment(theComment);
                } else {
                    if(principal.getName()!=null) {
                        theComment.setEmail(principal.getName());
                        Optional<User> user = userService.findByEmail(principal.getName());
                        if (user.isPresent()) {
                            theComment.setName(user.get().getName());
                        }
                    }
                    Post post = optionalPost.get();
                    post.addComment(theComment);
                    postService.savePost(post);
                }
            }
        }

        return "redirect:/"+id.toString();
    }

    @GetMapping("/{id}/editcomment/{commentId}")
    public String editComment(@PathVariable Long id, @PathVariable Long commentId, Model model, Principal principal) {

        Optional<Post> optionalPost = postService.findPostById(id);
        Optional<Comment> optionalComment = commentService.findCommentById(commentId);
        if(optionalPost.isPresent() && optionalComment.isPresent() && isPrincipalOwnerOfComment(principal, optionalComment.get())) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            Comment comment = optionalComment.get();
            model.addAttribute("commentNew", comment);
            return "posts/post-details";
        } else {
            return "redirect:/"+id.toString();
        }
    }

    @GetMapping("{id}/deletecomment/{commentId}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId, Principal principal ) {
        Optional<Comment> optionalComment = commentService.findCommentById(commentId);
        if (optionalComment.isPresent() && isPrincipalOwnerOfComment(principal, optionalComment.get())) {
            commentService.deleteCommentById(commentId);
        }
        return "redirect:/"+id.toString();
    }

    private boolean isPrincipalOwnerOfComment(Principal principal, Comment comment) {
        boolean admin = false;
        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isPresent() && user.get().getRoles()!=null) {
            for (Role role:user.get().getRoles()) {
                if (role.getRole().equals("ROLE_ADMIN")) {
                    admin = true;
                    break;
                }
            }
        }
        return admin || (principal != null && !comment.getEmail().isEmpty() && principal.getName().equals(comment.getEmail()));
    }

}

package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Comment;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.entity.Tasks;
import com.twd.SpringSecurityJWT.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Créer un commentaire
    @PostMapping("/addproject")
     // Ensure the user has the 'USER' role
    public Comment createComment(@RequestParam String content,
                                 @RequestBody Tasks task,
                                 @RequestBody OurUsers user) {
        return commentService.createComment(content, task, user);
    }

    // Récupérer tous les commentaires
    @PostMapping("/getallcomments")
    @PreAuthorize("hasRole('USER')") // Ensure the user has the 'USER' role
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    // Récupérer un commentaire par ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')") // Ensure the user has the 'USER' role
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    // Supprimer un commentaire
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')") // Ensure the user has the 'USER' role
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}

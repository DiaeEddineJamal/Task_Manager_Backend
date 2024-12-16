package com.twd.SpringSecurityJWT.service;


import com.twd.SpringSecurityJWT.entity.Comment;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.entity.Tasks;
import com.twd.SpringSecurityJWT.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Créer un commentaire
    public Comment createComment(String content, Tasks task, OurUsers user) {
        Comment comment = new Comment(content, task, user);
        return commentRepository.save(comment);
    }

    // Récupérer tous les commentaires
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Récupérer un commentaire par ID
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    // Supprimer un commentaire
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

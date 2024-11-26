package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.PasswordResetToken;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(OurUsers user);
    void deleteByUser(OurUsers user);
}
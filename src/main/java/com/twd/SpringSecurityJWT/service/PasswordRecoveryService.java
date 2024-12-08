package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.entity.PasswordResetToken;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import com.twd.SpringSecurityJWT.repository.PasswordResetTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    @Autowired
    private OurUserRepo userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public ReqRes initiatePasswordReset(String email) {
        ReqRes response = new ReqRes();

        try {
            OurUsers user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Delete any existing reset tokens
            tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

            // Generate new reset token
            String token = generateUniqueToken();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

            tokenRepository.save(resetToken);

            // Send reset email
            sendPasswordResetEmail(user, token);

            response.setStatusCode(200);
            response.setMessage("Password reset link sent");
            response.setResetToken(token);
        } catch (RuntimeException ex) {
            if (ex.getMessage().equals("User not found")) {
                response.setStatusCode(500);
                response.setMessage("User doesn't exist with the provided email.");
            } else {
                response.setStatusCode(500);
                response.setMessage("An unexpected error occurred.");
            }
        }

        return response;
    }


    @Transactional
    public ReqRes resetPassword(ReqRes request) {
        ReqRes response = new ReqRes();

        // Find token
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getResetToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Check token expiry
        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new RuntimeException("Token has expired");
        }

        // Validate passwords match
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Update user password
        OurUsers user = resetToken.getUser();
        if(user==null){
            throw new RuntimeException("User not found");

        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Delete used token
        tokenRepository.delete(resetToken);

        response.setStatusCode(200);
        response.setMessage("Password reset successfully");
        return response;
    }

    private String generateUniqueToken() {
        return UUID.randomUUID().toString();
    }

    private void sendPasswordResetEmail(OurUsers user, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("To reset your password, use the following token: " + token);

        mailSender.send(mailMessage);
    }
}
package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.service.PasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordRecoveryController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/forgot-password")
    public ResponseEntity<ReqRes> forgotPassword(@RequestBody ReqRes request) {
        ReqRes response = passwordRecoveryService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ReqRes> resetPassword(@RequestBody ReqRes request) {
        ReqRes response = passwordRecoveryService.resetPassword(request);
        return ResponseEntity.ok(response);
    }
}
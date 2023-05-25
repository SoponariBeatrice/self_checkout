package com.example.selfcheckout2.controller;

import com.example.selfcheckout2.auth.LoginRequest;
import com.example.selfcheckout2.auth.MessageResponse;
import com.example.selfcheckout2.auth.SignupRequest;
import com.example.selfcheckout2.service.AuthService;
import com.example.selfcheckout2.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    //@Resource(name = "authService")
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/signup")
    public MessageResponse registerUser(@RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }
    @GetMapping("/checker")
    public String checker()
    {
        return "you are authorized";
    }

    @GetMapping("/user/me")
    public UserDetailsImpl getUserEmail(){
        return authService.getLoggedUserDetails();
    }
}

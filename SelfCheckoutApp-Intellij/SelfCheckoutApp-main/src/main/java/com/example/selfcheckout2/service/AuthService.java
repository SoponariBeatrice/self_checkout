package com.example.selfcheckout2.service;
import com.example.selfcheckout2.auth.LoginRequest;
import com.example.selfcheckout2.auth.MessageResponse;
import com.example.selfcheckout2.auth.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    MessageResponse registerUser(SignupRequest signUpRequest);
}

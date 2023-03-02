package com.example.selfcheckout;

import java.util.List;

public class LoginResponse {
    public final List<String> roles;
    public String token;
    public String type = "Bearer";
    public Long id;
    public String username;
    public String email;

    public LoginResponse(List<String> roles, String token, Long id, String username, String email) {
        this.roles = roles;
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}

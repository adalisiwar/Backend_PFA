package com.fooddelivery.user_service.dto;

public class LoginResponse {

    private String token;
    private String username;

    // Required by Jackson
    public LoginResponse() {
    }

    public LoginResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {   // important
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {  // important
        this.username = username;
    }
}
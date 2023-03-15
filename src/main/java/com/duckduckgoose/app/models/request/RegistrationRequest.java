package com.duckduckgoose.app.models.request;

import jakarta.validation.constraints.Size;

public class RegistrationRequest {

    @Size(min = 1, max = 20, message = "Usernames must be between 1 and 20 characters long")
    private String username;

    @Size(min = 8, message = "Passwords must be at least 8 characters long")
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

package com.example.cyclingclub;

import java.io.Serializable;

public class User implements Serializable {
    private final String email;
    private final String username;
    private final String password;
    private final String role;
    private final String salt;

    public User(String email, String username, String password, String role, String salt)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role;}

    public String getSalt() { return salt; }
}

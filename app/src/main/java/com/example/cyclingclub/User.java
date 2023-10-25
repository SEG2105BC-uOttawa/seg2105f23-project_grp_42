package com.example.cyclingclub;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String username;
    private String role;
    private String salt;
    private String password;

    public User() { }

    public User(String email, String username, String role, String password, String salt)
    {
        this.email = email;
        this.username = username;
        this.role = role;
        this.salt = salt;
        this.password = password;
    }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role;}

    public String getSalt() { return salt; }
}

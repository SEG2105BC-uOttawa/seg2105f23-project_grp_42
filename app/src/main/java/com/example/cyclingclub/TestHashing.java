package com.example.cyclingclub;

import java.util.Base64;

public class TestHashing {
    public static void main(String[] args) {
        try {
            // Generate a random salt
            byte[] salt = Utils.generateSalt();

            // The password to be hashed
            String password = "admin";

            // Hash the password
            String hashedPassword = Utils.hashPasswordPBKDF2(password, salt);
            String saltString = Base64.getEncoder().encodeToString(salt);

            // Print the hashed password
            System.out.println("Hashed password: " + hashedPassword);
            System.out.println("Salt: " + saltString);

            // The password to be hashed
            String testPassword = "admin";

            // Hash the password
            String test = Utils.hashPasswordPBKDF2(testPassword, Base64.getDecoder().decode("yxgR8rdVb9q6P4NPvexREQ=="));
            System.out.println(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.example.cyclingclub;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InputValidator {
    private static InputValidator instance;

    private static final String EMAIL_PATTERN = "^(?!.*\\.\\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String NAME_PATTERN = "^[\\p{L}'-]+(\\s[\\p{L}'-]+)*$";
    private static final String USERNAME_PATTERN = "^[A-Za-z]{2,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$";

    private InputValidator() { }

    public static InputValidator getInstance() {
        if (instance == null) {
            instance = new InputValidator();
        }
        return instance;
    }

    /* Validate an email */
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /* Validate a name */
    public boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /* 1 lowercase & uppercase letter, 1 digit, 1 special character, no spaces, minimum 8 characters */
    public boolean isStrongPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /* Validate a username */
    public boolean isValidUsername(String name) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}

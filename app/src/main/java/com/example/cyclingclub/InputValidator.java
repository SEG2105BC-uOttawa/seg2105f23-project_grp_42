package com.example.cyclingclub;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Utility class for input validation.
 */
public class InputValidator {
    private static InputValidator instance;

    private static final String EMAIL_PATTERN = "^(?!.*\\.\\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String NAME_PATTERN = "^[\\p{L}'-]+(\\s[\\p{L}'-]+)*$";
    private static final String USERNAME_PATTERN = "^[A-Za-z]{2,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$";

    private InputValidator() { }

    /**
     * Get the instance of InputValidator.
     * @return The InputValidator instance.
     */
    public static InputValidator getInstance() {
        if (instance == null) {
            instance = new InputValidator();
        }
        return instance;
    }

    /**
     * Validate an email.
     * @param email The email to validate.
     * @return True if the email is valid, false otherwise.
     */
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validate a name.
     * @param name The name to validate.
     * @return True if the name is valid, false otherwise.
     */
    public boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Validate a strong password.
     * @param password The password to validate.
     * @return True if the password is strong, false otherwise.
     */
    public boolean isStrongPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Validate a username.
     * @param name The username to validate.
     * @return True if the username is valid, false otherwise.
     */
    public boolean isValidUsername(String name) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
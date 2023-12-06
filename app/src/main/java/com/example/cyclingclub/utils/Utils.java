package com.example.cyclingclub.utils;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.util.Base64;
import android.util.Log;

/**
 * Utility class for general purpose operations, such as password hashing and salt generation, input validation, and more.
 */
public class Utils {

    private static Utils instance;

    private static final String EMAIL_PATTERN = "^(?!.*\\.\\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String NAME_PATTERN = "^[\\p{L}'-]+(\\s[\\p{L}'-]+)*$";
    private static final String USERNAME_PATTERN = "^[A-Za-z]{2,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$";
    private static final String NUMBER_PATTERN = "^[0-9]+(\\.[0-9]+)?$";
    private static final String DATE_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private static final String STRING_PATTERN = "^[A-Za-z][A-Za-z0-9 ]*$";
    private static final String SOCIAL_MEDIA_PATTERN = "^(https?:\\/\\/)?(www\\.)?((facebook|twitter|instagram|linkedin|x)\\.com|plus\\.google\\.com)\\/.*$";
    private static final String PHONE_NUMBER_PATTERN = "^\\d{10}$";

    private Utils() { }

    /**
     * Get the instance of InputValidator.
     *
     * @return The InputValidator instance.
     */
    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    /**
     * Hash a password using PBKDF2 with a provided salt.
     *
     * @param password The password to be hashed.
     * @param salt The salt used for hashing.
     * @return The hashed password as a Base64-encoded string.
     * @throws Exception If there is an issue during the hashing process.
     */
    public static String hashPasswordPBKDF2(String password, byte[] salt) throws Exception {
        int iterations = 10000;
        int keyLength = 256; /* Key length in bits */

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash); // Use the Base64 class from the Java Standard Library
    }

    /**
     * Generate a random salt for use in password hashing.
     *
     * @return The generated salt as a byte array.
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Returns the hashed password. Just a normal getter method.
     *
     * @param password The password that the user inputted
     * @param salt Randomly generated salt
     * @return Will return the hashed password
     */
    public static String retHashedPassword(String password, byte[] salt) {
        try {
            return Utils.hashPasswordPBKDF2(password, salt);
        } catch (Exception e) {
            Log.e("createFirebaseAccount", "Hashing password failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Validate an email.
     *
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
     *
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
     *
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
     *
     * @param name The username to validate.
     * @return True if the username is valid, false otherwise.
     */
    public boolean isValidUsername(String name) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Validate a number.
     *
     * @param number The name to validate.
     * @return True if the number is valid, false otherwise.
     */
    public boolean isValidNumber(String number) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    /**
     * Validate a date.
     *
     * @param date The name to validate.
     * @return True if the number is valid, false otherwise.
     */
    public boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    /**
     * Validates a string based on the defined STRING_PATTERN.
     * The string is considered valid if it starts with a letter and can contain numbers and spaces.
     *
     * @param string The string to validate.
     * @return True if the string is valid, false otherwise.
     */
    public boolean isValidString(String string) {
        Pattern pattern = Pattern.compile(STRING_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Validates a social media link based on the defined SOCIAL_MEDIA_PATTERN.
     * The link is considered valid if it follows the standard URL format.
     *
     * @param s The social media link to validate.
     * @return True if the link is valid, false otherwise.
     */
    public boolean isValidSocialMediaLink(String link) {
        Pattern pattern = Pattern.compile(SOCIAL_MEDIA_PATTERN);
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();
    }

    /**
     * Validates a phone number based on the defined PHONE_NUMBER_PATTERN.
     * The phone number is considered valid if it contains exactly 10 digits.
     *
     * @param phoneNumber The phone number to validate.
     * @return True if the phone number is valid, false otherwise.
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
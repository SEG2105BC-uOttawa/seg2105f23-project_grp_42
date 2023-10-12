package com.example.cyclingclub;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import android.util.Base64;

/**
 * Utility class for security-related operations, such as password hashing and salt generation.
 */
public class SecurityUtils {

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
        return Base64.encodeToString(hash, Base64.NO_WRAP);
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
}
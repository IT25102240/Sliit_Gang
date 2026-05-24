package com.wedding.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// OOP: Utility class — password hashing using SHA-256
// FIX: Passwords are never stored in plain text anymore.
//      All passwords are hashed before saving to users.txt
//      and the hash is compared on login — the plain-text password
//      is never kept anywhere.
public class PasswordUtil {

    // Hash a plain-text password using SHA-256
    // Returns a 64-char hex string  e.g. "a665a45920422f9d417e..."
    public static String hash(String plainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(plainText.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is always available in standard Java — this cannot happen
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    // Check if a plain-text password matches a stored hash
    public static boolean verify(String plainText, String storedHash) {
        return hash(plainText).equals(storedHash);
    }
}

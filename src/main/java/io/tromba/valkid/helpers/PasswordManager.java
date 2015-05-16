package io.tromba.valkid.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Password helper class.
 */
public class PasswordManager {

    private String salt;

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        if (null == salt) {
            this.salt = generateSalt();
        }
        return salt;
    }

    public String generateSalt() {
        byte[] time = String.valueOf(System.currentTimeMillis()).getBytes();
        SecureRandom secureRandom = new SecureRandom(time);
        String salt = String.valueOf(secureRandom.generateSeed(32));
        return salt;
    }

    public String encrypt(String password) {
        final String algorithm = "SHA-512";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Algorithm " + algorithm + " not found: " + nsae.getMessage());
        }
        byte[] encryptedPassword = messageDigest.digest((getSalt() + password).getBytes());
        return String.valueOf(encryptedPassword);
    }
}

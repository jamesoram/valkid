package io.tromba.valkid.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Password helper class.
 */
public class PasswordManager {

    private String salt;

    public PasswordManager() {
        this.salt = generateSalt();
    }

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
        final String algorithm = "SHA1PRNG";
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance(algorithm);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Algorithm " + algorithm + " not found: " + nsae.getMessage());
        }
        byte[] salt = new byte[32];
        secureRandom.setSeed(System.currentTimeMillis());
        secureRandom.nextBytes(salt);
        return new String(Base64.getEncoder().encode(salt));
    }

    public String encrypt(String password) {
        final String algorithm = "SHA-512";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Algorithm " + algorithm + " not found: " + nsae.getMessage());
        }
        byte[] raw = messageDigest.digest((salt + password).getBytes());
        return new String(Base64.getEncoder().encode(raw));
    }
}

package io.tromba.valkid.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Password helper class.
 */
public class PasswordManager {

    private byte[] salt;

    public PasswordManager() {
        this.salt = generateSalt();
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getSalt() {
        if (null == salt) {
            this.salt = generateSalt();
        }
        return salt;
    }

    public byte[] generateSalt() {
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
        return salt;
    }

    public byte[] encrypt(String password) {
        final String algorithm = "SHA-512";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Algorithm " + algorithm + " not found: " + nsae.getMessage());
        }
        return messageDigest.digest((salt + password).getBytes());
    }
}

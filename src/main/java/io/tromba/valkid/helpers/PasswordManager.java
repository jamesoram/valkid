package io.tromba.valkid.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * Password helper class.
 */
public class PasswordManager {

    private String salt;
    private static final Logger LOGGER = Logger.getLogger(PasswordManager.class.getName());

    public PasswordManager() {
        this.salt = generateSalt();
    }

    public void setSalt(String salt) {
        LOGGER.info("setting salt");
        this.salt = salt;
    }

    public String getSalt() {
        LOGGER.info("getting salt");
        if (null == salt) {
            this.salt = generateSalt();
        }
        return salt;
    }

    public String generateSalt() {
        LOGGER.info("generating salt");
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
        LOGGER.info("encrypting password");
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

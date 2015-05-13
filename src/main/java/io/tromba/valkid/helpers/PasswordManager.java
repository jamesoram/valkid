package io.tromba.valkid.helpers;

/**
 * Password helper class.
 */
public class PasswordManager {

    private String secret;

    public PasswordManager() {
        this.secret = "in development";
    }

    public PasswordManager(String secret) {
        this.secret = secret;
    }

    public String encrypt(String password) {
        // magic
        return password + secret;
    }
}

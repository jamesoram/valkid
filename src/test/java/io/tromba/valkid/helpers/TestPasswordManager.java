package io.tromba.valkid.helpers;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for the password manager.
 */
public class TestPasswordManager {

    @Test(groups = "passwordManager")
    public void testEncrypt() {
        PasswordManager passwordManager = new PasswordManager();
        String result = passwordManager.encrypt("test");
        String result2 = new PasswordManager().encrypt("test");
        assertThat("Encryption with different hashes should be different", result, not(equalTo(result2)));
    }

    @Test(groups = "passwordManager")
    public void testGenerateSaltChangesInEveryMillisecond() {
        // salt should be different in different ms
        PasswordManager passwordManager = new PasswordManager();
        String firstSalt = passwordManager.generateSalt();
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            throw new RuntimeException("The test was cancelled while sleeping");
        }

        String secondSalt = passwordManager.generateSalt();
        assertThat("Salt should be different for every millisecond", firstSalt, not(equalTo(secondSalt)));
    }
}

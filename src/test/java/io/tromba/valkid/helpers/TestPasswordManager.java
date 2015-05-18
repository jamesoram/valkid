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

        byte[] result = passwordManager.encrypt("test");
        // reruns still generate the same hashes.. ?

        byte[] result2 = new PasswordManager().encrypt("test");

        assertThat(result, not(equalTo(result2)));
    }

    @Test(groups = "passwordManager")
    public void testGenerateSaltChangesInEveryMillisecond() {
        // salt should be different in different ms
        PasswordManager passwordManager = new PasswordManager();
        byte[] firstSalt = passwordManager.generateSalt();
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            // do nothing
        }

        byte[] secondSalt = passwordManager.generateSalt();
        assertThat("Salt should be different for every millisecond", firstSalt, not(equalTo(secondSalt)));
    }
}

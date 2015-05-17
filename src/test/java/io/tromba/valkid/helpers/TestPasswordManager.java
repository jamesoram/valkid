package io.tromba.valkid.helpers;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by jao on 16/05/15.
 */
public class TestPasswordManager {

    @Test(groups = "passwordManager")
    public void testEncrypt() {
        PasswordManager passwordManager = new PasswordManager();

        String result = passwordManager.encrypt("test");
        // reruns still generate the same hashes.. ?

        String result2 = new PasswordManager().encrypt("test");

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

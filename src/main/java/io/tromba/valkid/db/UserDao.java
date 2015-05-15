package io.tromba.valkid.db;

import io.tromba.valkid.helpers.PasswordManager;
import org.mongodb.morphia.Datastore;

import java.security.SecureRandom;

/**
 * Data access object for users.
 */
public class UserDao {

    private Datastore dataStore;
    private UserEntity userEntity;

    public UserDao(Datastore dataStore) {
        this.dataStore = dataStore;
        userEntity = new UserEntity();
    }

    public void setFirstName(String firstName) {
        userEntity.setFirstName(firstName);
    }

    public void setLastNaMe(String lastName) {
        userEntity.setLastName(lastName);
    }

    public void setEmail(String email) {
        userEntity.setEmail(email);
    }

    public void setPassword(String password) {
        byte[] time = String.valueOf(System.currentTimeMillis()).getBytes();
        SecureRandom secureRandom = new SecureRandom(time);
        String salt = String.valueOf(secureRandom.generateSeed(32));
        userEntity.setSalt(salt);
        userEntity.setPassword(new PasswordManager(salt).encrypt(password));
    }

    public void save() {
        String currentTime = String.valueOf(System.currentTimeMillis());
        if (null == userEntity.getJoinDate()) {
            userEntity.setJoinDate(currentTime);
        }
        userEntity.setLastUpdated(currentTime);
        dataStore.save(userEntity);
    }
}

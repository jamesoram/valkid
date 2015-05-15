package io.tromba.valkid.db;

import io.tromba.valkid.helpers.PasswordManager;
import org.mongodb.morphia.Datastore;

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
        userEntity.setPassword(new PasswordManager().encrypt(password));
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

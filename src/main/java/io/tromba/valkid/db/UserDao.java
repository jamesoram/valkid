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
        PasswordManager passwordManager = new PasswordManager();
        userEntity.setPassword(passwordManager.encrypt(password));
    }

    public void save() {
        dataStore.save(userEntity);
    }
}

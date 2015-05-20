package io.tromba.valkid.db;

import io.tromba.valkid.helpers.PasswordManager;
import org.mongodb.morphia.Datastore;

/**
 * Data access object for users.
 */
public class UserDao {

    private Datastore dataStore;

    public UserDao(Datastore dataStore) {
        this.dataStore = dataStore;
    }

    public void create(String firstName, String lastName, String email, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        setPassword(userEntity, password);
        save(userEntity);
    }

    private void save(UserEntity userEntity) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        if (null == userEntity.getJoinDate()) {
            userEntity.setJoinDate(currentTime);
        }
        userEntity.setLastUpdated(currentTime);
        dataStore.save(userEntity);
    }

    private void setPassword(UserEntity userEntity, String password) {
        PasswordManager passwordManager = new PasswordManager();
        final String salt = passwordManager.getSalt();
        userEntity.setSalt(salt);
        userEntity.setPassword(passwordManager.encrypt(password));
    }


//    public List<UserEntity> findAll() {
//        userEntity
//    }
}

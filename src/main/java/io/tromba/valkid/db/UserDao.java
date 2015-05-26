package io.tromba.valkid.db;

import io.tromba.valkid.exceptions.NoSuchUserException;
import io.tromba.valkid.helpers.PasswordManager;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Data access object for users.
 */
public class UserDao {

    private Datastore dataStore;

    public UserDao(Datastore dataStore) {
        this.dataStore = dataStore;
    }

    public User create(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        setPassword(user, password);
        save(user);
        user.setPassword("****");
        return user;
    }

    private void save(User user) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        if (null == user.getJoinDate()) {
            user.setJoinDate(currentTime);
        }
        user.setLastUpdated(currentTime);
        dataStore.save(user);
    }

    private void setPassword(User user, String password) {
        PasswordManager passwordManager = new PasswordManager();
        final String salt = passwordManager.getSalt();
        user.setSalt(salt);
        user.setPassword(passwordManager.encrypt(password));
    }


    public List<User> findAll() {
        return dataStore.find(User.class).asList();
    }

    public User findByEmail(String email) throws NoSuchUserException {
        User user = dataStore.find(User.class).field("email").equal(email).get();
        if (null == user) {
            throw new NoSuchUserException();
        }
        return user;
    }
}

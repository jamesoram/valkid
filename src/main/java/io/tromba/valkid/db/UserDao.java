package io.tromba.valkid.db;

import io.tromba.valkid.exceptions.NoSuchUserException;
import io.tromba.valkid.helpers.PasswordManager;
import org.mongodb.morphia.Datastore;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object for users.
 */
public class UserDao {

    private Datastore dataStore;
    private static Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public UserDao(Datastore dataStore) {
        this.dataStore = dataStore;
    }

    public User create(String firstName, String lastName, String email, String password) {
        LOGGER.log(Level.INFO, "dao creating user " + email);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        setPassword(user, password);
        save(user);
        return user;
    }

    private void save(User user) {
        LOGGER.log(Level.INFO, "saving user " + user.getEmail());
        String currentTime = String.valueOf(System.currentTimeMillis());
        if (null == user.getJoinDate()) {
            user.setJoinDate(currentTime);
        }
        user.setLastUpdated(currentTime);
        dataStore.save(user);
    }

    private void setPassword(User user, String password) {
        LOGGER.log(Level.INFO, "setting password for user " + user.getEmail());
        PasswordManager passwordManager = new PasswordManager();
        final String salt = passwordManager.getSalt();
        user.setSalt(salt);
        user.setPassword(passwordManager.encrypt(password));
    }

    public List<User> findAll() {
        LOGGER.log(Level.INFO, "finding all users");
        return dataStore.find(User.class).asList();
    }

    public User findByEmail(String email) throws NoSuchUserException {
        LOGGER.log(Level.INFO, "finding user " + email);
        User user = dataStore.find(User.class).field("email").equal(email).get();
        if (null == user) {
            LOGGER.log(Level.WARNING, "user not found: " + email);
            throw new NoSuchUserException();
        }
        return user;
    }

    public User deleteByEmail(String email) throws NoSuchUserException {
        LOGGER.log(Level.INFO, "deleting user " + email);
        User result = dataStore
                .findAndDelete(
                        dataStore
                                .find(User.class)
                                .field("email")
                                .equal(email));
        if (null == result) {
            LOGGER.log(Level.WARNING, "user not found: " + email);
            throw new NoSuchUserException();
        }
        return result;
    }
}

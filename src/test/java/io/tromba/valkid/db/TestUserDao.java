package io.tromba.valkid.db;

import io.tromba.valkid.exceptions.NoSuchUserException;
import org.mockito.Mockito;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Tests for the User data access object.
 */
public class TestUserDao {

    private Datastore datastore;
    private Query<User> userQuery;
    private UserDao userDao;
    private static final String email = "someone@somewhere.com";
    private final String firstName = "james";
    private final String lastName = "test";
    private final String password = "secret";

    @BeforeClass
    private void setUp() {
        this.datastore = Mockito.mock(Datastore.class);
        this.userQuery = Mockito.mock(Query.class);
        userDao = new UserDao(datastore);
    }

    @Test(groups = "userDao")
    public void testUserCreated() {
        when(datastore.save()).thenReturn(new LinkedList<Key<Object>>());
        User user = userDao.create(firstName, lastName,email, password);
        // check that the data has been saved to the database.
        Mockito.verify(datastore, Mockito.times(1)).save(user);
    }

    @Test(groups = "userDao")
    public void testUsersFound() {
        List<User> users = new ArrayList<User>();
        users.add(new User());
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find
                (User.class)
                .asList())
                .thenReturn(users);
        assertThat("Find all should find users", userDao.findAll(), not(empty()));
    }

    @Test(groups = "userDao")
    public void testNoUsersFound() {
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find
                (User.class)
                .asList())
                .thenReturn(new ArrayList<User>());
        assertThat("Find all should not find anything when there are not users", userDao.findAll(), empty());
    }

    @Test(groups = "userDao")
    public void testUserFoundByEmail() {
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(fieldEnd.equal(email)).thenReturn(userQuery);
        User user = new User();
        user.setEmail(email);
        when(userQuery.get()).thenReturn(user);
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find(User.class).field("email")).thenReturn(fieldEnd);
        User foundUser = null;

        try {
            foundUser = userDao.findByEmail(email);
        } catch (NoSuchUserException e) {
            org.testng.Assert.fail("no users were found when expecting to find by email");
        }
        assertThat("User should be found by email", foundUser, equalTo(user));
    }

    @Test(groups = "userDao", expectedExceptions = NoSuchUserException.class)
    public void testUserNotFoundByEmail() throws NoSuchUserException {
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(fieldEnd.equal(email)).thenReturn(userQuery);
        when(userQuery.get()).thenReturn(null);
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find(User.class).field("email")).thenReturn(fieldEnd);

        userDao.findByEmail(email);
        // exception should be thrown
    }

    @Test(groups = "userDao")
    public void testDeleteExistingUser() throws NoSuchUserException {
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        User user = new User();
        user.setEmail(email);
        when(datastore.findAndDelete(userQuery)).thenReturn(user);
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find(User.class).field("email")).thenReturn(fieldEnd);
        when(datastore.find(User.class).field("email").equal(email)).thenReturn(userQuery);

        userDao.deleteByEmail(email);
        Mockito.verify(datastore, Mockito.times(1)).findAndDelete(userQuery);
    }

    @Test(groups = "userDao", expectedExceptions = NoSuchUserException.class)
    public void testDeleteNonExistingUser() throws NoSuchUserException {
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(fieldEnd.equal(email)).thenReturn(userQuery);
        User user = new User();
        user.setEmail(email);
        when(userQuery.get()).thenReturn(user);
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find(User.class).field("email")).thenReturn(fieldEnd);
        when(datastore.find(User.class).field("email").equal(email)).thenReturn(userQuery);
        when(datastore.findAndDelete(userQuery)).thenReturn(null);

        userDao.deleteByEmail(email);
        Mockito.verify(datastore, Mockito.timeout(0)).delete(User.class);
    }

    @Test(groups = "userDao", enabled = false)
    public void testUpdateUser() {
        FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
        when(fieldEnd.equal(email)).thenReturn(userQuery);
        User user = new User();
        user.setEmail(email);
        when(userQuery.get()).thenReturn(user);
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find(User.class).field("email")).thenReturn(fieldEnd);
        when(datastore.find(User.class).field("email").equal(email)).thenReturn(userQuery);
        UpdateOperations updateOperations = Mockito.mock(UpdateOperations.class);
        when(datastore.createUpdateOperations(User.class)).thenReturn(updateOperations);
        when(updateOperations.add("firstName", firstName)).thenReturn(updateOperations);
        when(updateOperations.add("lastName", lastName)).thenReturn(updateOperations);
        when(updateOperations.add("email", email)).thenReturn(updateOperations);
        when(datastore.update(user, updateOperations));

        // do assert
    }
}

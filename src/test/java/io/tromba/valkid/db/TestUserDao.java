package io.tromba.valkid.db;

import org.mockito.Mockito;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

/**
 * Tests for the User data access object.
 */
public class TestUserDao {

    Datastore datastore;
    Query<User> userQuery;

    @BeforeClass
    private void setUp() {
        this.datastore = Mockito.mock(Datastore.class);
        this.userQuery = Mockito.mock(Query.class);
    }

    @Test(groups = "userDao")
    public void testUserCreated() {
        final String created = "created";
        final String firstName = "james";
        final String lastName = "test";
        final String email = "test@example.com";
        final String password = "secret";
        when(datastore.save()).thenReturn(new LinkedList<Key<Object>>());
        UserDao userDao = new UserDao(datastore);
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
        UserDao userDao = new UserDao(datastore);
        assertThat("Find all should find users", userDao.findAll(), not(empty()));
    }

    @Test(groups = "userDao")
    public void testNoUsersFound() {
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find
                (User.class)
                .asList())
                .thenReturn(new ArrayList<User>());

        UserDao userDao = new UserDao(datastore);
        assertThat("Find all should not find anything when there are not users", userDao.findAll(), empty());
    }

    @Test(groups = "userDao")
    public void testUserFoundByEmail() {
    }
}

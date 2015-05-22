package io.tromba.valkid.db;

import io.tromba.valkid.resources.UserResource;
import org.mockito.Mockito;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
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

    @Test(groups = "UserDao")
    public void testUserCreated() {
        final String created = "created";
        final String firstName = "james";
        final String lastName = "test";
        final String email = "test@example.com";
        final String password = "secret";
        Datastore datastore = Mockito.mock(Datastore.class);
        when(datastore.save()).thenReturn(new LinkedList<Key<Object>>());
        UserDao userDao = new UserDao(datastore);
        UserResource userResource = new UserResource(created, userDao);

        userResource.createUser(firstName, lastName, email, password, "empty");
        // check that the data has been saved to the database.
        Mockito.verify(datastore, Mockito.times(1));
    }

    @Test(groups = "UserDao")
    public void testUsersFound() {
        Datastore datastore = Mockito.mock(Datastore.class);
        List<User> users = new ArrayList<User>();
        users.add(new User());
        Query<User> userQuery = Mockito.mock(Query.class);
        when(datastore.find(User.class)).thenReturn(userQuery);
        when(datastore.find
                (User.class)
                .asList())
                .thenReturn(users);

        UserDao userDao = new UserDao(datastore);
        assertThat(userDao.findAll(), not(empty()));
    }

    // test for find when db empty and nonempty

    // tests for find by id
}

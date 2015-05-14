package io.tromba.valkid.db;

import io.tromba.valkid.resources.UserResource;
import org.mockito.Mockito;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.testng.annotations.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.when;

/**
 * Tests for the User data access object.
 */
public class TestUserDao {

    @Test
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
}

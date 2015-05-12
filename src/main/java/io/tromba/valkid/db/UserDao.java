package io.tromba.valkid.db;

import org.mongodb.morphia.Datastore;

/**
 * Created by jao on 12/05/15.
 */
public class UserDao {

    private Datastore datastore;

    public UserDao(Datastore datastore) {
        this.datastore = datastore;
    }
}

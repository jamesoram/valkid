package io.tromba.valkid.db;

import com.mongodb.Mongo;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by jao on 09/05/15.
 */
public class MongoClientManager implements Managed {

    private final Mongo mongo;

    public MongoClientManager(Mongo mongo) {
        this.mongo = mongo;
    }

    public void start() {
        // should already be started
    }

    public void stop() {
        mongo.close();
    }
}

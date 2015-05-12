package io.tromba.valkid.db;

import com.mongodb.Mongo;
import io.dropwizard.lifecycle.Managed;
import io.tromba.valkid.ValkidConfiguration;

/**
 * Created by jao on 09/05/15.
 */
public class MongoClientManager implements Managed {

    private final Mongo mongo;

    public MongoClientManager(ValkidConfiguration configuration) {
        this(startMongo(configuration));
    }

    public MongoClientManager(Mongo mongo) {
        this.mongo = mongo;
    }

    private static Mongo startMongo(ValkidConfiguration configuration) {
        return new Mongo(configuration.getMongoHost(), configuration.getMongoPort());
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void start() {
        // should already be started
    }

    public void stop() {
        mongo.close();
    }
}

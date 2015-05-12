package io.tromba.valkid.db;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;
import io.tromba.valkid.ValkidConfiguration;

/**
 * Created by jao on 09/05/15.
 */
public class MongoClientManager implements Managed {

    private final MongoClient mongo;

    public MongoClientManager(ValkidConfiguration configuration) {
        this(startMongo(configuration));

    }

    public MongoClientManager(MongoClient mongo) {
        this.mongo = mongo;
    }

    private static MongoClient startMongo(ValkidConfiguration configuration) {
        return new MongoClient(configuration.getMongoHost(), configuration.getMongoPort());
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public void start() {
        // should already be started
    }

    public void stop() {
        mongo.close();
    }
}

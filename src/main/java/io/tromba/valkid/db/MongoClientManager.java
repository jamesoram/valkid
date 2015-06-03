package io.tromba.valkid.db;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;
import io.tromba.valkid.ValkidConfiguration;

import java.util.logging.Logger;

/**
 * Created by jao on 09/05/15.
 */
public class MongoClientManager implements Managed {

    private final MongoClient mongo;
    private static final Logger LOGGER = Logger.getLogger(MongoClientManager.class.getName());

    public MongoClientManager(ValkidConfiguration configuration) {
        this(startMongo(configuration));

    }

    public MongoClientManager(MongoClient mongo) {
        this.mongo = mongo;
    }

    private static MongoClient startMongo(ValkidConfiguration configuration) {
        LOGGER.info("Starting mongo client");
        return new MongoClient(configuration.getMongoHost(), configuration.getMongoPort());
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public void start() {
        // should already be started
    }

    public void stop() {
        LOGGER.info("closing mongo client");
        mongo.close();
    }
}

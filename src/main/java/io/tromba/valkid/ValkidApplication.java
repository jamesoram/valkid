package io.tromba.valkid;

import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.tromba.valkid.db.MongoClientManager;
import io.tromba.valkid.db.UserDao;
import io.tromba.valkid.healthchecks.MongoHealthCheck;
import io.tromba.valkid.resources.UserResource;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.logging.Logger;

/**
 * Application set up class.
 */
public class ValkidApplication extends Application<ValkidConfiguration> {

    private static final Logger LOGGER = Logger.getLogger(ValkidApplication.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting Valkid");
        new ValkidApplication().run(args);
    }

    @Override
    public void run(ValkidConfiguration configuration, Environment environment) {
        LOGGER.info("Initializing Valkid");
        MongoClientManager mongoClientManager = new MongoClientManager(configuration);
        Datastore datastore = new Morphia().createDatastore(new MongoClient(), configuration.getMongodb());
        final UserDao userDao = new UserDao(datastore);

        environment.lifecycle().manage(mongoClientManager);

        // add resources
        final UserResource userResource = new UserResource(userDao);
        environment.jersey().register(userResource);
        // add healthchecks
        environment.healthChecks().register("mongo", new MongoHealthCheck(mongoClientManager.getMongo()));
    }

    @Override
    public String getName() {
        return "valkid";
    }

    @Override
    public void initialize(Bootstrap<ValkidConfiguration> bootstrap) {
    }
}

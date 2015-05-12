package io.tromba.valkid;

import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.tromba.valkid.db.MongoClientManager;
import io.tromba.valkid.healthchecks.MongoHealthCheck;
import io.tromba.valkid.resources.UserResource;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Application set up class.
 */
public class ValkidApplication extends Application<ValkidConfiguration> {

    public static void main(String[] args) throws Exception {
        new ValkidApplication().run(args);
    }

    @Override
    public void run(ValkidConfiguration configuration, Environment environment) {
        MongoClientManager mongoClientManager = new MongoClientManager(configuration);

        environment.lifecycle().manage(mongoClientManager);
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(new MongoClient(), configuration.getMongodb());

        // add resources
        final UserResource userResource = new UserResource(configuration.getCreatedMessage());
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

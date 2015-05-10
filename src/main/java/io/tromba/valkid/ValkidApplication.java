package io.tromba.valkid;

import com.mongodb.Mongo;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.tromba.valkid.db.MongoClientManager;
import io.tromba.valkid.healthchecks.MongoHealthCheck;
import io.tromba.valkid.resources.UserResource;

/**
 * Application set up class.
 */
public class ValkidApplication extends Application<ValkidConfiguration> {

    public static void main(String[] args) throws Exception {
        new ValkidApplication().run(args);
    }

    @Override
    public void run(ValkidConfiguration configuration, Environment environment) {
        // set up database
        Mongo mongo = new Mongo(configuration.getMongoHost(), configuration.getMongoPort());
        MongoClientManager mongoClientManager = new MongoClientManager(mongo);
        environment.lifecycle().manage(mongoClientManager);
        // add resources
        final UserResource userResource = new UserResource(configuration.getCreatedMessage());
        environment.jersey().register(userResource);
        // add healthchecks
        environment.healthChecks().register("mongo", new MongoHealthCheck(mongo));
    }

    @Override
    public String getName() {
        return "valkid";
    }

    @Override
    public void initialize(Bootstrap<ValkidConfiguration> bootstrap) {
    }
}

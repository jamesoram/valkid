package io.tromba.valkid;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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
        final UserResource userResource = new UserResource(configuration.getCreatedMessage());
        environment.jersey().register(userResource);
    }

    @Override
    public String getName() {
        return "valkid";
    }

    @Override
    public void initialize(Bootstrap<ValkidConfiguration> bootstrap) {

    }
}

package io.tromba.valkid.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.List;

/**
 * Healthcheck for MongoDB.
 */
public class MongoHealthCheck extends HealthCheck {

    private MongoClient mongo;

    public MongoHealthCheck(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Override
    protected Result check() {
        try {
            List<ServerAddress> addresses = mongo.getAllAddress();
            if (null == addresses || addresses.isEmpty()) {
                return Result.unhealthy("MongoDB down, empty address list");
            }
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("MongoDB down, exception thrown: " + e.getMessage());
        }
    }
}

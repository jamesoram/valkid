package io.tromba.valkid.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.Mongo;

/**
 * Created by jao on 09/05/15.
 */
public class MongoHealthCheck extends HealthCheck {

    private Mongo mongo;

    public MongoHealthCheck(Mongo mongo) {
        this.mongo = mongo;
    }

    @Override
    protected Result check() {
        try {
            mongo.getAllAddress();
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("MongoDB down");
        }
    }
}

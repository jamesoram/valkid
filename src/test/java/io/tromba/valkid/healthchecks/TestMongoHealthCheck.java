package io.tromba.valkid.healthchecks;

import com.codahale.metrics.health.HealthCheck.Result;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Basic tests for the MongoDB healthcheck.
 */
public class TestMongoHealthCheck {

    @Test(groups = "healthCheck")
    public void testMongoIsHealthy() {
        MongoClient mongo = Mockito.mock(MongoClient.class);
        ServerAddress testAddress = new ServerAddress("0.0.0.0");
        List<ServerAddress> testAddressList = new LinkedList<ServerAddress>();
        testAddressList.add(testAddress);
        when(mongo.getAllAddress()).thenReturn(testAddressList);

        MongoHealthCheck mongoHealthCheck = new MongoHealthCheck(mongo);
        assertThat("MongoDB result was not healthy", mongoHealthCheck.check(), equalTo(Result.healthy()));
    }

    @Test(groups = "healthCheck")
    public void testMongoIsUnhealthyWithException() {
        MongoClient mongo = Mockito.mock(MongoClient.class);
        when(mongo.getAllAddress()).thenThrow(NullPointerException.class);

        MongoHealthCheck mongoHealthCheck = new MongoHealthCheck(mongo);

        assertThat("MongoDB healthcheck result didn't catch exception", Result.healthy(),
                not(equalTo(mongoHealthCheck.check())));
    }

    @Test(groups = "healthCheck")
    public void testMongoIsUnhealthyWhenThereAreNoConnectedServers() {
        MongoClient mongo = Mockito.mock(MongoClient.class);
        when(mongo.getAllAddress()).thenReturn(new LinkedList<ServerAddress>());

        MongoHealthCheck mongoHealthCheck = new MongoHealthCheck(mongo);
        Assert.assertNotEquals(Result.healthy(), mongoHealthCheck.check(),
                "Mongo was considered healthy despite there being no connected servers");
        assertThat("Mongo was considered healthy despite there being no connected servers", Result.healthy(),
                not(equalTo(mongoHealthCheck.check())));
    }
}

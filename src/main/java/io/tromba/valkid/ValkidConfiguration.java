package io.tromba.valkid;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Configuration class for the application.
 */
public class ValkidConfiguration extends Configuration {

    @NotEmpty
    private String createdMessage;

    @NotEmpty
    private String mongoHost;

    @Min(1)
    @Max(65535)
    private int mongoPort;

    @NotEmpty
    private String mongodb;

    @JsonProperty
    public String getCreatedMessage() {
        return createdMessage;
    }

    @JsonProperty
    public void setCreatedMessage(String createdMessage) {
        this.createdMessage = createdMessage;
    }

    @JsonProperty
    public String getMongoHost() {
        return mongoHost;
    }

    @JsonProperty
    public void setMongoHost(String mongoHost) {
        this.mongoHost = mongoHost;
    }

    @JsonProperty
    public int getMongoPort() {
        return mongoPort;
    }

    @JsonProperty
    public void setMongoPort(int mongoPort) {
        this.mongoPort = mongoPort;
    }

    @JsonProperty
    public String getMongodb() {
        return mongodb;
    }

    @JsonProperty
    public void setMongodb(String mongodb) {
        this.mongodb = mongodb;
    }
}

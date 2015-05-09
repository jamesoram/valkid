package io.tromba.valkid;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Configuration class for the application.
 */
public class ValkidConfiguration extends Configuration {

    @NotEmpty
    private String createdMessage;

    @JsonProperty
    public String getCreatedMessage() {
        return createdMessage;
    }

    @JsonProperty
    public void setCreatedMessage(String createdMessage) {
        this.createdMessage = createdMessage;
    }
}

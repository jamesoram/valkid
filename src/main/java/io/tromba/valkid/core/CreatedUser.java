package io.tromba.valkid.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * Created representation class.
 */
public class CreatedUser {

    @Length(max = 256)
    private String content;

    public CreatedUser() {

    }

    public CreatedUser(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}

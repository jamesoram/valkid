package io.tromba.valkid.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * Created representation class.
 */
public class Created  {

    @Length(max = 256)
    private String content;

    public Created() {

    }

    public Created(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}

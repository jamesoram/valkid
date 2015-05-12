package io.tromba.valkid.resources;

import com.codahale.metrics.annotation.Timed;
import io.tromba.valkid.core.CreatedUser;
import io.tromba.valkid.db.UserEntity;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User resource class.
 */

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final String created;

    public UserResource(String created) {
        this.created = created;
    }

    @POST
    @Timed
    public CreatedUser createUser(@FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
                              @FormParam("email") String email, @FormParam("password") String password,
                              @FormParam("app_token") String appToken) {
        // do magic here
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        // encrypt with password manager
//        user.setPassword(password);
//        user.save
        return new CreatedUser();
    }
}

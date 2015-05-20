package io.tromba.valkid.resources;

import com.codahale.metrics.annotation.Timed;
import io.tromba.valkid.core.CreatedUser;
import io.tromba.valkid.db.UserDao;
import io.tromba.valkid.db.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User resource class.
 */

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final String created;
    private final UserDao userDao;

    public UserResource(String created, UserDao userDao) {
        this.created = created;
        this.userDao = userDao;
    }

    @POST
    @Timed
    public CreatedUser createUser(@FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
                              @FormParam("email") String email, @FormParam("password") String password,
                              @FormParam("app_token") String appToken) {
        userDao.create(firstName, lastName, email, password);
        return new CreatedUser(created);
    }

    @GET
    @Timed
    public List<UserEntity> getUsers() {
        return userDao.findAll();
    }
}

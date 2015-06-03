package io.tromba.valkid.resources;

import com.codahale.metrics.annotation.Timed;
import io.tromba.valkid.db.User;
import io.tromba.valkid.db.UserDao;
import io.tromba.valkid.exceptions.NoSuchUserException;

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
    public User createUser(@FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
                              @FormParam("email") String email, @FormParam("password") String password,
                              @FormParam("app_token") String appToken) {
        return userDao.create(firstName, lastName, email, password);
    }

    @GET
    @Timed
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @GET
    @Timed
    public User getUser(@PathParam("email") String email) {
        try {
            return userDao.findByEmail(email);
        } catch (NoSuchUserException e) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Timed
    public void deleteUser(@FormParam("email") String email) {
        try {
            userDao.deleteByEmail(email);
        } catch (NoSuchUserException nse) {

        }
    }
}

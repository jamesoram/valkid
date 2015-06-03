package io.tromba.valkid.resources;

import com.codahale.metrics.annotation.Timed;
import io.tromba.valkid.db.User;
import io.tromba.valkid.db.UserDao;
import io.tromba.valkid.exceptions.NoSuchUserException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User resource class.
 */

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;
    private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @POST
    @Timed
    public User createUser(@FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
                              @FormParam("email") String email, @FormParam("password") String password,
                              @FormParam("app_token") String appToken) {
        LOGGER.log(Level.INFO, "user creation requested, email: " + email);
        return userDao.create(firstName, lastName, email, password);
    }

    @GET
    @Path("/all")
    @Timed
    public List<User> getUsers() {
        LOGGER.log(Level.INFO, "find all users requested");
        return userDao.findAll();
    }

    @GET
    @Timed
    public User getUser(@PathParam("email") String email) {
        LOGGER.log(Level.INFO, "get user requested, email: " + email);
        try {
            return userDao.findByEmail(email);
        } catch (NoSuchUserException e) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Timed
    public void deleteUser(@FormParam("email") String email) {
        LOGGER.log(Level.INFO, "delete user requested, email: " + email);
        try {
            userDao.deleteByEmail(email);
        } catch (NoSuchUserException nse) {

        }
    }
}

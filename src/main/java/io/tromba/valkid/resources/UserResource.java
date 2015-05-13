package io.tromba.valkid.resources;

import com.codahale.metrics.annotation.Timed;
import io.tromba.valkid.core.CreatedUser;
import io.tromba.valkid.db.UserDao;

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
        userDao.setFirstName(firstName);
        userDao.setLastNaMe(lastName);
        userDao.setEmail(email);
        userDao.setPassword(password);
        return new CreatedUser();
    }
}

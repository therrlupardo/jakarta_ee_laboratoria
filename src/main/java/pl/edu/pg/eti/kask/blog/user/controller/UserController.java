package pl.edu.pg.eti.kask.blog.user.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author mateusz.buchajewicz
 * REST Controller for {@link User}
 */
@Path("users")
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) { this.userService = userService; }

    /**
     * Searches for all users
     *
     * @return list of users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(userService.findAll()).build();
    }

}

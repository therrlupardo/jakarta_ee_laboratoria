package pl.edu.pg.eti.kask.blog.user.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.user.dto.CreateUserRequest;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * REST Controller for {@link User}
 */
@Path("users")
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService; }

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

    /**
     * Searches for user with given id
     * @param id unique user identifier
     * @return Response(200) if user found with entity as body, Response(404) if user doesn't exist
     */
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<User> user = userService.findById(id);
        return user.isPresent() ? Response.ok(user).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     *  Creates new user
     * @param request data of user to be created
     * @return Response(201) if user created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserRequest request) {
        User user;
        try {
            user = CreateUserRequest.convertToEntity(request);
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        userService.createUser(user);

        URI getUserById = URI.create(
                UriBuilder.fromResource(UserController.class).build().getPath() +
                        UriBuilder.fromMethod(UserController.class, "getUserById").build(user.getId())
        );

        return Response.created(getUserById).build();
    }
}

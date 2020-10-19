package pl.edu.pg.eti.kask.blog.user.servlet;

import pl.edu.pg.eti.kask.blog.user.dto.UserDto;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;
import pl.edu.pg.eti.kask.blog.utils.ServletUtils;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * HTTP Servlet for user entity
 */
@WebServlet(urlPatterns = {
        UserServlet.Paths.USERS + "/*"
})
public class UserServlet extends HttpServlet {
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtils.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            } else if (path.matches(Patterns.USER_BY_ID)) {
                getUserById(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Returns user with id specified as wildcard in request path
     * If user doesn't exist, 404 will be returned
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws IOException thrown if any input/output exception
     */
    private void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        Long id = ServletUtils.getIdFromWildcard(request);

        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            response.setContentType("application/json");
            response.getWriter().write(jsonb.toJson(UserDto.convertFromEntity(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /**
     * Returns list of all users
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws IOException thrown if any input/output exception
     */
    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        response.setContentType("application/json");
        response.getWriter().write(jsonb.toJson(
                userService.findAll()
                        .stream()
                        .map(UserDto::convertFromEntity)
                        .collect(Collectors.toList())));
    }

    /**
     * Available uris
     */
    public static class Paths {
        public static final String USERS = "/api/users";
    }

    /**
     * Available wildcard patterns
     */
    public static class Patterns {
        /**
         * Empty wildcard
         */
        public static final String USERS = "^/?$";
        /**
         * Numerical wildcard (ie. user's id)
         */
        public static final String USER_BY_ID = "^/[0-9]+/?$";
    }

}

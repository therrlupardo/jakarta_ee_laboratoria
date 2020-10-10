package pl.edu.pg.eti.kask.blog.user.servlet;

import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;
import pl.edu.pg.eti.kask.blog.user.servlet.UserServlet.Patterns;
import pl.edu.pg.eti.kask.blog.utils.ServletUtils;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        UserServlet.Paths.USERS + "/*"
})
public class UserServlet extends HttpServlet {
    public static class Paths {
        public static final String USERS = "/api/users";
    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER_BY_ID = "^/[0-9]+/?$";
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "" : request.getPathInfo();
        String servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USERS)) {
                System.out.println("GET_USERS");
                getUsers(request, response);
                return;
            } else if (path.matches(Patterns.USER_BY_ID)) {
                getUserById(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        Long id = Long.parseLong(ServletUtils.parseRequestPath(request).replaceAll("/", ""));

        Optional<User> user = userService.findById(id);

        if(user.isPresent()) {
            response.setContentType("application/json");
            response.getWriter().write(jsonb.toJson(user));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        response.setContentType("application/json");
        response.getWriter().write(jsonb.toJson(userService.findAll()));
    }

}

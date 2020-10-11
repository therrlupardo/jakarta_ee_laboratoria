package pl.edu.pg.eti.kask.blog.avatar.servlet;

import pl.edu.pg.eti.kask.blog.avatar.service.AvatarService;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;
import pl.edu.pg.eti.kask.blog.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Servlet for user's avatar
 */
@WebServlet(urlPatterns = {AvatarServlet.Paths.AVATARS + "/*"})
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    /**
     * Available api uris
     */
    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    /**
     * Patterns for wildcards
     */
    public static class Patterns {
        /**
         * Matches numerical variable, used as unique identifier of user
         */
        public static final String AVATAR_BY_USER_ID = "^/[0-9]+/?$";
    }

    /**
     * Request parameters
     */
    public static class Parameters {
        /**
         * Indicates that request content if part of avatar
         */
        public static final String AVATAR = "avatar";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtils.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR_BY_USER_ID)) {
                getAvatarForUserId(request, response);
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Searches for avatar of user, which id was specified in request
     * If user doesn't exist, 404 will be returned
     * If avatar doesn't exist, 404 will be returned
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getAvatarForUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        AvatarService avatarService = (AvatarService) request.getServletContext().getAttribute("avatarService");
        Long id = ServletUtils.getIdFromWildcard(request);
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            try {
                byte[] avatar = avatarService.findAvatarByUser(user.get());
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
            } catch (FileNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtils.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR_BY_USER_ID)) {
                createAvatar(request, response);
            }
        }
    }

    /**
     * Creates avatar for user, which id was specified in request
     * To add avatar, request type should be multipart/from-data, and content should be marked with name=Parameters.AVATAR
     * If user doesn't exist, 404 will be returned
     * If user already has avatar, 409 will be returned
     * If avatar was not specified as request content, 204 will be returned
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void createAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        AvatarService avatarService = (AvatarService) request.getServletContext().getAttribute("avatarService");
        Long id = ServletUtils.getIdFromWildcard(request);
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                try {
                    avatarService.create(user.get(), avatar.getInputStream());
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (FileAlreadyExistsException e) {
                    response.sendError(HttpServletResponse.SC_CONFLICT);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtils.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR_BY_USER_ID)) {
                updateAvatar(request, response);
            }
        }
    }

    /**
     * Updates avatar for user, which id was specified in request
     * To update avatar, request type should be multipart/from-data, and content should be marked with name=Parameters.AVATAR
     * If user doesn't exist, 404 will be returned
     * If user has no avatar, 404 will be returned
     * If avatar was not specified as request content, 204 will be returned
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        AvatarService avatarService = (AvatarService) request.getServletContext().getAttribute("avatarService");
        Long id = ServletUtils.getIdFromWildcard(request);
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                try {
                    avatarService.updateAvatar(user.get(), avatar.getInputStream());
                } catch (FileNotFoundException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtils.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR_BY_USER_ID)) {
                System.out.println("DELETE_AVATAR()");
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Deletes avatar of user, which id was specified in request
     * If user doesn't exist, 404 will be returned
     * If user has no avatar, 404 will be returned
     * @param request
     * @param response
     * @throws IOException
     */
    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        AvatarService avatarService = (AvatarService) request.getServletContext().getAttribute("avatarService");
        Long id = ServletUtils.getIdFromWildcard(request);
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            try {
                avatarService.delete(user.get());
            } catch (FileNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

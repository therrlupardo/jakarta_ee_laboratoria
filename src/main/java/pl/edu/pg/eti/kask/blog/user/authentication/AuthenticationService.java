package pl.edu.pg.eti.kask.blog.user.authentication;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.user.context.UserContext;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service which menages user's authentication
 */
@ApplicationScoped
@NoArgsConstructor
public class AuthenticationService implements Serializable {

    private UserService userService;

    /**
     * Context of user, stores information about logged in use
     */
    private UserContext userContext;

    @Inject
    public AuthenticationService(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }

    /**
     * Checks if user's credentials are valid. If it's valid, sets user as principal in UserContext.
     *
     * @param login    user's login
     * @param password user's password
     * @return true if user's credentials are valid, otherwise returns false
     */
    public boolean authenticate(String login, String password) {
        Optional<User> user = userService.find(login, Sha256HashingUtility.hash(password));
        if (user.isPresent()) {
            userContext.setPrincipal(login);
            return true;
        }
        return false;
    }
}

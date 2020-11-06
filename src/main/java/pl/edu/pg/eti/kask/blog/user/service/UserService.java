package pl.edu.pg.eti.kask.blog.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.entity.UserRoles;
import pl.edu.pg.eti.kask.blog.user.repository.UserRepository;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for user entity
 */
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed(UserRoles.USER)
public class UserService implements Serializable {

    private UserRepository userRepository;
    private SecurityContext securityContext;
    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public UserService(UserRepository userRepository, SecurityContext securityContext, Pbkdf2PasswordHash pbkdf) {
        this.userRepository = userRepository;
        this.securityContext = securityContext;
        this.pbkdf = pbkdf;
    }

    /**
     * @return returns all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Searches for user with given id
     *
     * @param id id of user to be found
     * @return user with given id as optional (can be empty)
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates user
     *
     * @param user user to be created
     */
    @PermitAll
    public void createUser(User user) {
        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            user.setRoles(List.of(UserRoles.USER));
        }
        user.setPassword(pbkdf.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login    user's login
     * @param password password hashed using {@link Sha256HashingUtility}
     * @return matching users data as optional (can be empty)
     */
    public Optional<User> find(String login, String password) {
        return userRepository.find(login, password);
    }

    /**
     * Searches for user with given name
     * @param name name of user
     * @return user as optional (may be empty
     */
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}

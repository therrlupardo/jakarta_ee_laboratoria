package pl.edu.pg.eti.kask.blog.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for user entity
 */
@NoArgsConstructor
@ApplicationScoped
public class UserService implements Serializable {

    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void createUser(User user) {
        userRepository.create(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login    user's login
     * @param password password hashed using {@link pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility}
     * @return matching users data as optional (can be empty)
     */
    public Optional<User> find(String login, String password) {
        return userRepository.find(login, password);
    }
}

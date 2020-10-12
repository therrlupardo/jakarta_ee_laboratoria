package pl.edu.pg.eti.kask.blog.user.service;

import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for user entity
 */
public class UserService {

    private UserRepository userRepository;

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
     * @param id id of user to be found
     * @return user with given id as optional (can be empty)
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates user
     * @param user user to be created
     */
    public void createUser(User user) {
        userRepository.create(user);
    }
}

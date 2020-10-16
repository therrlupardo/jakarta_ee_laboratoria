package pl.edu.pg.eti.kask.blog.datastore;

import pl.edu.pg.eti.kask.blog.serialization.CloningUtility;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * Data store containing data of application
 */
@ApplicationScoped
public class DataStore {

    /**
     * Set of all users
     */
    private final Set<User> users = new HashSet<>();

    /**
     * Searches for all user entities.
     *
     * @return list of all users
     */
    public synchronized List<User> findAllUsers() {
        return new ArrayList<>(users)
                .stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Searches for user with given id
     *
     * @param id unique identifier of user
     * @return user with given id as optional (which can be empty)
     */
    public Optional<User> findUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Adds user to DataStore, assigning id based on current users
     *
     * @param user user to be added to DataStore
     * @throws IllegalArgumentException
     */
    public synchronized void createUser(User user) throws IllegalArgumentException {
        user.setId(findAllUsers().stream().mapToLong(User::getId).max().orElse(0) + 1);
        users.add(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login user's login
     * @param password user's password (hashed with {@link pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility}
     * @return matching user's data as optional (can be empty)
     */
    public Optional<User> findUser(String login, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }
}

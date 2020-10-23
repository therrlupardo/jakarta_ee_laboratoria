package pl.edu.pg.eti.kask.blog.datastore;

import pl.edu.pg.eti.kask.blog.datastore.utils.DataStoreUtils;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.utils.CloningUtility;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * <p>
 * Data store containing data of usesr
 */
@ApplicationScoped
public class UserDataStore {

    /**
     * Set of all users
     */
    private final Set<User> users = new HashSet<>();

    /**
     * Searches for all user entities.
     *
     * @return list of all users
     */
    public synchronized List<User> findAll() {
        List<User> userList = new ArrayList<>(users);
        return userList.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Searches for user with given id
     *
     * @param id unique identifier of user
     * @return user with given id as optional (which can be empty)
     */
    public Optional<User> findById(Long id) {
        List<User> userList = users.stream()
                .filter(user -> user.getId().equals(id))
                .collect(Collectors.toList());
        return userList.stream().findFirst().map(CloningUtility::clone);
    }

    /**
     * Adds user to DataStore, assigning new id to it
     *
     * @param user user to be added to DataStore
     */
    public synchronized void create(User user) {
        user.setId(DataStoreUtils.getNextId(findAll()));
        users.add(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login    user's login
     * @param password user's password (hashed with {@link Sha256HashingUtility}
     * @return matching user's data as optional (can be empty)
     */
    public Optional<User> findByCredentials(String login, String password) {
        List<User> userList = users.stream()
                .filter(user -> user.areCredentialsValid(login, password))
                .collect(Collectors.toList());
        return userList.stream().findFirst().map(CloningUtility::clone);
    }
}

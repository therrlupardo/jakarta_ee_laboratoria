package pl.edu.pg.eti.kask.blog.user.repository;

import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;
import pl.edu.pg.eti.kask.blog.datastore.DataStore;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for user entity
 */
@Dependent
public class UserRepository implements CrudRepository<User> {
    private final DataStore dataStore;

    @Inject
    public UserRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * @return returns list of all users
     */
    @Override
    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    /**
     * Deletes specified user
     *
     * @param user user to be deleted
     */
    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces data of user with given id with data specified as param user
     *
     * @param id   id of user, which data should be updated
     * @param user data of user after modification
     */
    @Override
    public void update(Long id, User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for user with given id
     *
     * @param id unique identifier of user
     * @return user with specified id as optional (can be empty)
     */
    @Override
    public Optional<User> findById(Long id) {
        return dataStore.findUserById(id);
    }

    /**
     * Creates user
     *
     * @param user user to be created
     */
    @Override
    public void create(User user) {
        dataStore.createUser(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login    user's login
     * @param password user's password (hashed with {@link Sha256HashingUtility}
     * @return matching user's data as optional (can be empty)
     */
    public Optional<User> find(String login, String password) {
        return dataStore.findUser(login, password);
    }
}

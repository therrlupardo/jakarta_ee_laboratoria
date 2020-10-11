package pl.edu.pg.eti.kask.blog.user.repository;

import jdk.jshell.spi.ExecutionControl;
import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;
import pl.edu.pg.eti.kask.blog.datastore.DataStore;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for user entity
 */
public class UserRepository implements CrudRepository<User> {
    private DataStore dataStore;

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
     * @param user user to be deleted
     */
    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces data of user with given id with data specified as param user
     * @param id id of user, which data should be updated
     * @param user data of user after modification
     */
    @Override
    public void update(Long id, User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for user with given id
     * @param id unique identifier of user
     * @return user with specified id as optional (can be empty)
     */
    @Override
    public Optional<User> findById(Long id) {
        return dataStore.findUserById(id);
    }

    /**
     * Creates user
     * @param user user to be created
     */
    @Override
    public void create(User user) {
        dataStore.createUser(user);
    }
}

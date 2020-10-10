package pl.edu.pg.eti.kask.blog.user.repository;

import pl.edu.pg.eti.kask.blog.datastore.DataStore;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private DataStore dataStore;

    public UserRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    public Optional<User> findUserById(Long id) {
        return dataStore.findUserById(id);
    }

    public void createUser(User user) {
        dataStore.createUser(user);
    }
}

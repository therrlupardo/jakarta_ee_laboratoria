package pl.edu.pg.eti.kask.blog.user.repository;

import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for user entity
 */
@Dependent
public class UserRepository implements CrudRepository<User> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return returns list of all users
     */
    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * Deletes specified user
     *
     * @param user user to be deleted
     */
    @Override
    public void delete(User user) {
        em.remove(em.find(User.class, user.getId()));
    }

    /**
     * Replaces data of user with given id with data specified as param user
     *
     * @param id   id of user, which data should be updated
     * @param user data of user after modification
     */
    @Override
    public void update(Long id, User user) {
        em.merge(user);
    }

    /**
     * Searches for user with given id
     *
     * @param name username of user
     * @return user with specified id as optional (can be empty)
     */
    public User findByName(String name) {
        return em.find(User.class, name);
    }

    /**
     * Creates user
     *
     * @param user user to be created
     */
    @Override
    public void create(User user) {
        em.persist(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login    user's login
     * @param password user's password (hashed with {@link Sha256HashingUtility}
     * @return matching user's data as optional (can be empty)
     */
    public Optional<User> find(String login, String password) {
        try {
            return Optional.of(em.createQuery("SELECT u FROM User u WHERE u.username = :login AND u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Searches for user with given username
     *
     * @param id identifier os user
     * @return matching user
     */
    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

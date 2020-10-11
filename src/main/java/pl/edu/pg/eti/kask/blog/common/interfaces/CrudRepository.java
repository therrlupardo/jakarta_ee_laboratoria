package pl.edu.pg.eti.kask.blog.common.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 *
 * Interface for CRUD repositories
 *
 * @param <T> type of data
 */
public interface CrudRepository<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    void create(T obj);

    void delete(T obj);

    void update(Long id, T obj);
}

package pl.edu.pg.eti.kask.blog.datastore.utils;

import pl.edu.pg.eti.kask.blog.common.interfaces.Entity;

import java.util.List;

/**
 * @author mateusz.buchajewicz
 *
 * Utils for DataStores
 */
public class DataStoreUtils {
    /**
     * Returns next available id for given list
     *
     * @param list list of elements of type T, which which next available id should be returned
     * @param <T>  type extending interface {@link Entity},
     * @return next available id in list
     */
   public static synchronized <T extends Entity> Long getNextId(List<T> list) {
        return list.stream()
                .mapToLong(T::getId)
                .max()
                .orElse(0) + 1;
    }
}

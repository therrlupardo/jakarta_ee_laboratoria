package pl.edu.pg.eti.kask.blog.avatar.repository;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.naming.OperationNotSupportedException;
import java.io.InputStream;

/**
 * @author mateusz.buchajewicz
 * Repository for user avatars
 */
@Dependent
@NoArgsConstructor
public class AvatarRepository {

    /**
     * Searches for avatar of given user
     *
     * @param user user which avatar should be returned
     * @return avatar of specified user as array of bytes
     */
    public byte[] findAvatarByUser(User user) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    /**
     * Deletes avatar of given user
     *
     * @param user user which avatar should be removed
     */
    public void deleteAvatar(User user) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    /**
     * Creates avatar for given user
     *
     * @param user   user for which avatar should be created
     * @param avatar avatar which should be assigned to given user
     */
    public void createAvatar(User user, InputStream avatar) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    /**
     * Updates avatar for given user
     *
     * @param user   user which avatar should be updated
     * @param avatar avatar which should be assigned to given user
     */
    public void updateAvatar(User user, InputStream avatar) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

}

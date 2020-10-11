package pl.edu.pg.eti.kask.blog.avatar.repository;

import pl.edu.pg.eti.kask.blog.datastore.DataStore;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Repository for user avatars
 */
public class AvatarRepository {
    private DataStore dataStore;

    public AvatarRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Searches for avatar of given user
     *
     * @param user user which avatar should be returned
     * @return avatar of specified user as array of bytes
     * @throws IOException
     */
    public byte[] findAvatarByUser(User user) throws IOException {
        return dataStore.findAvatarFor(user);
    }

    /**
     * Deletes avatar of given user
     *
     * @param user user which avatar should be removed
     * @throws IOException
     */
    public void deleteAvatar(User user) throws IOException {
        dataStore.deleteAvatarFor(user);
    }

    /**
     * Creates avatar for given user
     * @param user user for which avatar should be created
     * @param avatar avatar which should be assigned to given user
     * @throws IOException
     */
    public void createAvatar(User user, InputStream avatar) throws IOException {
        dataStore.createAvatarFor(user, avatar);
    }

    /**
     * Updates avatar for given user
     * @param user user which avatar should be updated
     * @param avatar avatar which should be assigned to given user
     * @throws IOException
     */
    public void updateAvatar(User user, InputStream avatar) throws IOException {
        dataStore.updateAvatarFor(user, avatar);
    }


}

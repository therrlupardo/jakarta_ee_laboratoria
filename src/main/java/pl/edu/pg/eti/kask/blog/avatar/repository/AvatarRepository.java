package pl.edu.pg.eti.kask.blog.avatar.repository;

import pl.edu.pg.eti.kask.blog.datastore.FileDataStore;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mateusz.buchajewicz
 * Repository for user avatars
 */
public class AvatarRepository {
    private final FileDataStore fileDataStore;

    public AvatarRepository(FileDataStore fileDataStore) {
        this.fileDataStore = fileDataStore;
    }

    /**
     * Searches for avatar of given user
     *
     * @param user user which avatar should be returned
     * @return avatar of specified user as array of bytes
     * @throws IOException thrown if any input/output exception
     */
    public byte[] findAvatarByUser(User user) throws IOException {
        return fileDataStore.findAvatarFor(user);
    }

    /**
     * Deletes avatar of given user
     *
     * @param user user which avatar should be removed
     * @throws IOException thrown if any input/output exception
     */
    public void deleteAvatar(User user) throws IOException {
        fileDataStore.deleteAvatarFor(user);
    }

    /**
     * Creates avatar for given user
     *
     * @param user   user for which avatar should be created
     * @param avatar avatar which should be assigned to given user
     * @throws IOException throw if any input/output exception
     */
    public void createAvatar(User user, InputStream avatar) throws IOException {
        fileDataStore.createAvatarFor(user, avatar);
    }

    /**
     * Updates avatar for given user
     *
     * @param user   user which avatar should be updated
     * @param avatar avatar which should be assigned to given user
     * @throws IOException throw if any input/output exception
     */
    public void updateAvatar(User user, InputStream avatar) throws IOException {
        fileDataStore.updateAvatarFor(user, avatar);
    }

}

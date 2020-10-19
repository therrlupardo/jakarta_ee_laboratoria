package pl.edu.pg.eti.kask.blog.avatar.service;

import pl.edu.pg.eti.kask.blog.avatar.repository.AvatarRepository;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mateusz.buchajewicz
 * Service for user's avatars
 */
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Searches for avatar of given user
     *
     * @param user user which avatar should be returned
     * @return avatar of specified user as array of bytes
     * @throws IOException throw if any input/output exception
     */
    public byte[] findAvatarByUser(User user) throws IOException {
        return avatarRepository.findAvatarByUser(user);
    }

    /**
     * Deletes avatar of given user
     *
     * @param user user which avatar should be removed
     * @throws IOException throw if any input/output exception
     */
    public void delete(User user) throws IOException {
        avatarRepository.deleteAvatar(user);
    }

    /**
     * Creates avatar for given user
     *
     * @param user   user for which avatar should be created
     * @param avatar avatar which should be assigned to given user
     * @throws IOException throw if any input/output exception
     */
    public void create(User user, InputStream avatar) throws IOException {
        avatarRepository.createAvatar(user, avatar);
    }

    /**
     * Updates avatar for given user
     *
     * @param user   user which avatar should be updated
     * @param avatar avatar which should be assigned to given user
     * @throws IOException throw if any input/output exception
     */
    public void updateAvatar(User user, InputStream avatar) throws IOException {
        avatarRepository.updateAvatar(user, avatar);
    }
}

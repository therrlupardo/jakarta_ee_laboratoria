package pl.edu.pg.eti.kask.blog.avatar.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.avatar.repository.AvatarRepository;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mateusz.buchajewicz
 * Service for user's avatars
 */
@LocalBean
@Stateless
@NoArgsConstructor
public class AvatarService {

    private AvatarRepository avatarRepository;

    @Inject
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Searches for avatar of given user
     *
     * @param user user which avatar should be returned
     * @return avatar of specified user as array of bytes
     * @throws OperationNotSupportedException thrown if operation is not supported
     */
    public byte[] findAvatarByUser(User user) throws OperationNotSupportedException {
        return avatarRepository.findAvatarByUser(user);
    }

    /**
     * Deletes avatar of given user
     *
     * @param user user which avatar should be removed
     * @throws OperationNotSupportedException thrown if operation is not supported
     */
    public void delete(User user) throws OperationNotSupportedException {
        avatarRepository.deleteAvatar(user);
    }

    /**
     * Creates avatar for given user
     *
     * @param user   user for which avatar should be created
     * @param avatar avatar which should be assigned to given user
     * @throws OperationNotSupportedException thrown if operation is not supported
     */
    public void create(User user, InputStream avatar) throws OperationNotSupportedException {
        avatarRepository.createAvatar(user, avatar);
    }

    /**
     * Updates avatar for given user
     *
     * @param user   user which avatar should be updated
     * @param avatar avatar which should be assigned to given user
     * @throws OperationNotSupportedException thrown if operation is not supported
     */
    public void updateAvatar(User user, InputStream avatar) throws OperationNotSupportedException {
        avatarRepository.updateAvatar(user, avatar);
    }
}

package pl.edu.pg.eti.kask.blog.datastore;

import pl.edu.pg.eti.kask.blog.serialization.CloningUtility;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * Data store containing data of application
 */
public class DataStore {

    private final Path rootPath;

    public DataStore(Path root) {
        this.rootPath = root;
    }

    /**
     * Set of all users
     */
    private Set<User> users = new HashSet<>();

    /**
     * Searches for all user entities.
     *
     * @return list of all users
     */
    public synchronized List<User> findAllUsers() {
        return new ArrayList<>(users)
                .stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Searches for user with given id
     *
     * @param id unique identifier of user
     * @return user with given id as optional (which can be empty)
     */
    public Optional<User> findUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Adds user to DataStore, assigning id based on current users
     *
     * @param user user to be added to DataStore
     * @throws IllegalArgumentException
     */
    public synchronized void createUser(User user) throws IllegalArgumentException {
        user.setId(findAllUsers().stream().mapToLong(User::getId).max().orElse(0) + 1);
        users.add(user);
    }

    /**
     * Creates avatar for user, if user has no avatar yet
     *
     * @param user   user for which avatar should be created
     * @param avatar avatar which should be added for user
     * @throws IOException (FileAlreadyExistsException) will be thrown if user has avatar
     */
    public synchronized void createAvatarFor(User user, InputStream avatar) throws IOException {
        if (avatarExistsFor(user)) {
            throw new FileAlreadyExistsException(user.getAvatarFileName());
        }
        Path file = getFilePath(user.getAvatarFileName());
        Files.copy(avatar, file);
    }

    /**
     * Searches for avatar of given user
     *
     * @param user user to which avatar is assigned
     * @return avatar of specified user
     * @throws IOException (FileNotFoundException) will be thrown if user has no avatar
     */
    public byte[] findAvatarFor(User user) throws IOException {
        if (!avatarExistsFor(user)) {
            throw new FileNotFoundException();
        }
        Path path = getFilePath(user.getAvatarFileName());
        return Files.readAllBytes(path);
    }


    /**
     * Replaces current avatar of user to new one
     *
     * @param user   user which avatar should be replaced
     * @param avatar avatar which should be added to user in place of previous one
     * @throws IOException (FileNotFoundException) will be thrown if user has no avatar
     */
    public synchronized void updateAvatarFor(User user, InputStream avatar) throws IOException {
        if (!avatarExistsFor(user)) {
            throw new FileNotFoundException();
        }
        Path file = getFilePath(user.getAvatarFileName());
        Files.copy(avatar, file, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Removes avatar of given user
     *
     * @param user user which avatar should be removed
     * @throws IOException (FileNotFoundException) will be thrown if user has no avatar
     */
    public synchronized void deleteAvatarFor(User user) throws IOException {
        if (!avatarExistsFor(user)) {
            throw new FileNotFoundException();
        }
        Path path = getFilePath(user.getAvatarFileName());
        Files.delete(path);
    }

    /**
     * Checks, if specified user has avatar
     *
     * @param user user, for which avatar is being searched for
     * @return true if user has avatar, false if not
     */
    private boolean avatarExistsFor(User user) {
        return Files.exists(getFilePath(user.getAvatarFileName()));
    }

    /**
     * Returns path to fileName, depending on rootPath
     * @param fileName name of file
     * @return path to file with specified name in rootPath
     */
    private Path getFilePath(String fileName) {
        return Paths.get(rootPath.toString(), fileName);
    }
}

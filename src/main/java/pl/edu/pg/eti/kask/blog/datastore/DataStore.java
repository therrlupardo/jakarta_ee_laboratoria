package pl.edu.pg.eti.kask.blog.datastore;

import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.common.interfaces.Entity;
import pl.edu.pg.eti.kask.blog.serialization.CloningUtility;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * Data store containing data of application
 */
@ApplicationScoped
public class DataStore {

    /**
     * Set of all users
     */
    private final Set<User> users = new HashSet<>();

    /**
     * Set of all articles
     */
    private final Set<Article> articles = new HashSet<>();

    /**
     * Set of all comments
     */
    private final Set<Comment> comments = new HashSet<>();

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
     * Adds user to DataStore, assigning new id to it
     *
     * @param user user to be added to DataStore
     */
    public synchronized void createUser(User user) {
        user.setId(getNextId(findAllUsers()));
        users.add(user);
    }

    /**
     * Searches for user with given credentials
     *
     * @param login    user's login
     * @param password user's password (hashed with {@link pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility}
     * @return matching user's data as optional (can be empty)
     */
    public Optional<User> findUser(String login, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(login) && user.getPassword().equals(password))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Searches for all articles
     *
     * @return list of all articles
     */
    public synchronized List<Article> findAllArticles() {
        return new ArrayList<>(articles).stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    /**
     * Adds article to dataStore, assigning new id to it
     *
     * @param article article to be added to DataStore
     */
    public synchronized void createArticle(Article article) {
        article.setId(getNextId(findAllArticles()));
        articles.add(article);
    }

    /**
     * Returns next available id for given list
     *
     * @param list list of elements of type T, which which next available id should be returned
     * @param <T>  type extending interface {@link Entity},
     * @return next available id in list
     */
    private synchronized <T extends Entity> Long getNextId(List<T> list) {
        return list.stream()
                .mapToLong(T::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Deletes article from DataStore
     *
     * @param article article to be deleted
     */
    public synchronized void deleteArticle(Article article) {
        articles.removeIf(a -> a.getId().equals(article.getId()));
        findAllCommentByArticleId(article.getId()).forEach(this::deleteComment);
    }

    /**
     * Searches for article with given id
     *
     * @param id id of article to be found
     * @return article with matching id as optional (can be empty)
     */
    public Optional<Article> findArticleById(Long id) {
        return articles.stream().filter(u -> u.getId().equals(id)).findFirst().map(CloningUtility::clone);
    }

    /**
     * Searches for all comments for given article
     *
     * @param articleId id of article
     */
    public List<Comment> findAllCommentByArticleId(Long articleId) {
        return comments.stream()
                .filter(c -> c.getArticleId().equals(articleId))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Searches for all comments
     *
     * @return list of all comments
     */
    public List<Comment> findAllComments() {
        return new ArrayList<>(comments).stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    /**
     * Creates comment
     *
     * @param comment data of comment to be created
     */
    public synchronized void createComment(Comment comment) {
        comment.setId(getNextId(findAllComments()));
        comments.add(comment);
    }

    /**
     * Deletes comment
     *
     * @param comment comment to be deleted
     */
    public void deleteComment(Comment comment) {
        comments.removeIf(c -> c.getId().equals(comment.getId()));
    }
}

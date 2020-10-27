package pl.edu.pg.eti.kask.blog.comment.repository;

import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;
import pl.edu.pg.eti.kask.blog.datastore.CommentDataStore;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for {@link Comment} class
 */
@Dependent
public class CommentRepository implements Serializable, CrudRepository<Comment> {

    private final CommentDataStore dataStore;

    @Inject
    public CommentRepository(CommentDataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Searches for comments for article with given id
     *
     * @param articleId id of article, which comments should be returned
     * @return list of comments under article
     */
    public List<Comment> findAllByArticleId(Long articleId) {
        return dataStore.findAllByParentId(articleId);
    }

    /**
     * Searches for comment with given id
     *
     * @param id id of comment to be found
     * @return comment with matching id as optional (can be empty)
     */
    @Override
    public Optional<Comment> findById(Long id) {
        return dataStore.findById(id);
    }

    /**
     * Searches for all comments
     *
     * @return list of all comments
     */
    @Override
    public List<Comment> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates comment
     *
     * @param comment data of comment, which should be created
     */
    @Override
    public void create(Comment comment) {
        dataStore.create(comment);
    }

    /**
     * Deletes specified comment
     *
     * @param comment comment to be deleted
     */
    @Override
    public void delete(Comment comment) {
        dataStore.delete(comment);
    }

    /**
     * Updates comment with given id
     *
     * @param id      id of comment, which should be updated
     * @param comment data of comment after update
     */
    @Override
    public void update(Long id, Comment comment) {
        dataStore.update(id, comment);
    }

    /**
     * Searches for comment with given id from article with given articleId
     *
     * @param articleId id of article
     * @param commentId id of comment
     * @return comment with matching id's as optional (can be empty)
     */
    public Optional<Comment> findOneByArticleId(Long articleId, Long commentId) {
        return dataStore.findOneByParentId(articleId, commentId);
    }
}

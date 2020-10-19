package pl.edu.pg.eti.kask.blog.comment.repository;

import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;
import pl.edu.pg.eti.kask.blog.datastore.DataStore;

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

    private final DataStore dataStore;

    @Inject
    public CommentRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Searches for comments for article with given id
     *
     * @param articleId id of article, which comments should be returned
     * @return list of comments under article
     */
    public List<Comment> findAllByArticleId(Long articleId) {
        return dataStore.findAllCommentByArticleId(articleId);
    }

    /**
     * Searches for comment with given id
     *
     * @param id id of comment to be found
     * @return comment with matching id as optional (can be empty)
     */
    @Override
    public Optional<Comment> findById(Long id) {
        return dataStore.findCommentById(id);
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
        dataStore.createComment(comment);
    }

    /**
     * Deletes specified comment
     *
     * @param comment comment to be deleted
     */
    @Override
    public void delete(Comment comment) {
        dataStore.deleteComment(comment);
    }

    /**
     * Updates comment with given id
     *
     * @param id      id of comment, which should be updated
     * @param comment data of comment after update
     */
    @Override
    public void update(Long id, Comment comment) {
        dataStore.updateComment(id, comment);
    }
}

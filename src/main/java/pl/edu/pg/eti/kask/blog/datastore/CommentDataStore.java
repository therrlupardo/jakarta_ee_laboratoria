package pl.edu.pg.eti.kask.blog.datastore;

import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.datastore.utils.DataStoreUtils;
import pl.edu.pg.eti.kask.blog.utils.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mateusz.buchajewicz
 * <p>
 * Data Store for {@link Comment}
 */
@ApplicationScoped
public class CommentDataStore {


    /**
     * Set of all comments
     */
    private final Set<Comment> comments = new HashSet<>();

    /**
     * Searches for all comments for given article
     *
     * @param articleId id of article
     */
    public List<Comment> findAllByParentId(Long articleId) {
        Stream<Comment> filtered = comments.stream()
                .filter(c -> c.getArticleId().equals(articleId));
        return filtered.map(CloningUtility::clone).collect(Collectors.toList());

    }

    /**
     * Searches for all comments
     *
     * @return list of all comments
     */
    public List<Comment> findAll() {
        List<Comment> commentList = new ArrayList<>(comments);
        return commentList.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    /**
     * Creates comment
     *
     * @param comment data of comment to be created
     */
    public synchronized void create(Comment comment) {
        comment.setId(DataStoreUtils.getNextId(findAll()));
        comment.setCreationTime(LocalDateTime.now());
        comments.add(comment);
    }

    /**
     * Deletes comment
     *
     * @param comment comment to be deleted
     */
    public void delete(Comment comment) {
        comments.removeIf(c -> c.getId().equals(comment.getId()));
    }

    /**
     * Searches for comment with given id
     *
     * @param id id of comment to be found
     * @return comment with matching id as optional (can be empty)
     */
    public Optional<Comment> findById(Long id) {
        List<Comment> commentList = comments.stream().filter(c -> c.getId().equals(id)).collect(Collectors.toList());
        return commentList.stream().findFirst().map(CloningUtility::clone);
    }

    /**
     * Updates comment with given id
     *
     * @param id      id of comment to be updated
     * @param comment data of comment after update
     */
    public void update(Long id, Comment comment) {
        comments.removeIf(c -> c.getId().equals(id));
        comments.add(comment);
    }

    /**
     * Searches for comment with given articleId and id
     *
     * @param articleId id of article
     * @param commentId id of comment
     * @return comment with matching articleId and own id
     */
    public Optional<Comment> findOneByParentId(Long articleId, Long commentId) {
        List<Comment> commentsFromArticle = findAllByParentId(articleId);
        List<Comment> filtered = commentsFromArticle.stream().filter(c -> c.getId().equals(commentId)).collect(Collectors.toList());
        return filtered.stream().findFirst();
    }
}

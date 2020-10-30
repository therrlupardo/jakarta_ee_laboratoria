package pl.edu.pg.eti.kask.blog.comment.repository;

import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for {@link Comment} class
 */
@RequestScoped
public class CommentRepository implements Serializable, CrudRepository<Comment> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Searches for comments for article with given id
     *
     * @param articleId id of article, which comments should be returned
     * @return list of comments under article
     */
    public List<Comment> findAllByArticleId(Long articleId) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.article.id = :articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();

    }

    /**
     * Searches for comment with given id
     *
     * @param id id of comment to be found
     * @return comment with matching id as optional (can be empty)
     */
    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    /**
     * Searches for all comments
     *
     * @return list of all comments
     */
    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    /**
     * Creates comment
     *
     * @param comment data of comment, which should be created
     */
    @Override
    public void create(Comment comment) {
        em.persist(comment);
    }

    /**
     * Deletes specified comment
     *
     * @param comment comment to be deleted
     */
    @Override
    public void delete(Comment comment) {
        em.remove(em.find(Comment.class, comment.getId()));
    }

    /**
     * Updates comment with given id
     *
     * @param id      id of comment, which should be updated
     * @param comment data of comment after update
     */
    @Override
    public void update(Long id, Comment comment) {
        em.merge(comment);
    }

    /**
     * Searches for comment with given id from article with given articleId
     *
     * @param articleId id of article
     * @param commentId id of comment
     * @return comment with matching id's as optional (can be empty)
     */
    public Optional<Comment> findOneByArticleId(Long articleId, Long commentId) {
        try {
            return Optional.of(
                    em.createQuery("SELECT c FROM Comment c WHERE c.id = :commentId AND c.article.id = :articleId", Comment.class)
                    .setParameter("articleId", articleId)
                    .setParameter("commentId", commentId)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}

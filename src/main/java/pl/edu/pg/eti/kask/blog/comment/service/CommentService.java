package pl.edu.pg.eti.kask.blog.comment.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.repository.CommentRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for {@link Comment}
 */
@Stateless
@LocalBean
@NoArgsConstructor
public class CommentService implements Serializable {

    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;

    @Inject
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * Searches for comments under article with given id
     *
     * @param articleId id of article, which comments should be found
     * @return list of comments
     */
    public List<Comment> findAllByArticleId(Long articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }

    /**
     * Creates comment
     *
     * @param comment data of comment to be created
     */
    public void createComment(Comment comment) {
        commentRepository.create(comment);
        articleRepository.findById(comment.getArticle().getId())
                .ifPresent(article -> article.getComments().add(comment));
    }

    /**
     * Deletes comment
     *
     * @param comment comment to be deleted
     */
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    /**
     * Searches for comment with given id
     *
     * @param id id of comment to be found
     * @return comment with matching id as optional (can be empty)
     */
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    /**
     * Updates comment
     *
     * @param id identifier of comment to update
     * @param comment data of updated comment
     */
    public void updateComment(Long id, Comment comment) {
        commentRepository.update(id, comment);
    }

    /**
     * Searches for comment with given id from article with given articleId
     *
     * @param articleId id of article
     * @param commentId id of comment
     * @return comment with matching id's as optional (can be empty)
     */
    public Optional<Comment> findOneByArticleId(Long articleId, Long commentId) {
        return commentRepository.findOneByArticleId(articleId, commentId);
    }
}

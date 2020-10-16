package pl.edu.pg.eti.kask.blog.comment.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.comment.dto.CommentsDto;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.repository.CommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 * Service for {@link pl.edu.pg.eti.kask.blog.comment.entity.Comment}
 */
@NoArgsConstructor
@ApplicationScoped
public class CommentService implements Serializable {

    private CommentRepository commentRepository;

    @Inject
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Searches for comments under article with given id
     * @param articleId id of article, which comments should be found
     * @return list of comments
     */
    public List<Comment> findAllByArticleId(Long articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }

    /**
     * Creates comment
     * @param comment data of comment to be created
     */
    public void createComment(Comment comment) {
        commentRepository.create(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}

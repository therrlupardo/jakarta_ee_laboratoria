package pl.edu.pg.eti.kask.blog.comment.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.repository.CommentRepository;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.entity.UserRoles;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for {@link Comment}
 */
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed(UserRoles.USER)
public class CommentService implements Serializable {

    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private SecurityContext securityContext;


    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, SecurityContext securityContext) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.securityContext = securityContext;
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
        User user = userService.findByName(securityContext.getCallerPrincipal().getName());
        comment.setUser(user);
        user.getComments().add(comment);
        commentRepository.create(comment);
//        articleRepository.findById(comment.getArticle().getId())
//                .ifPresent(article -> article.getComments().add(comment));
    }

    /**
     * Deletes comment
     *
     * @param comment comment to be deleted
     */
    public void delete(Comment comment) throws EJBAccessException {
        Comment originalComment = commentRepository.findById(comment.getId()).orElseThrow();
        if (securityContext.isCallerInRole(UserRoles.ADMIN)
                || originalComment.getUser().getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            comment.getUser().getComments().remove(originalComment);
            commentRepository.delete(comment);
        } else {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
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
    public void updateComment(Long id, Comment comment) throws EJBAccessException {
        Comment originalComment = commentRepository.findById(id).orElseThrow();
        if (securityContext.isCallerInRole(UserRoles.ADMIN)
                || originalComment.getUser().getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            commentRepository.update(id, comment);
        } else {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());
        }
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

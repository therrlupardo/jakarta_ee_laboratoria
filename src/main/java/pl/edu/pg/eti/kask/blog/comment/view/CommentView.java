package pl.edu.pg.eti.kask.blog.comment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.model.CommentModel;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * View bean for {@link pl.edu.pg.eti.kask.blog.comment.entity.Comment}
 */
@Named
@SessionScoped
public class CommentView implements Serializable {
    private CommentService commentService;
    private ArticleService articleService;

    @EJB
    public void setArticleService(ArticleService articleService) { this.articleService = articleService; }

    @EJB
    public void setCommentService(CommentService commentService) { this.commentService = commentService; }

    /**
     * Unique identifier of comment, passed as path param {id}
     */
    @Getter
    @Setter
    private Long id;

    /**
     * Data of comment
     */
    @Getter
    private CommentModel comment;

    /**
     * Initializes data
     *
     * @throws IOException thrown if any input/output exception
     */
    public void init() throws IOException {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()) {
            this.comment = CommentModel.convertFromEntity(comment.get(), articleService);
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Comment not found");
        }
    }

}

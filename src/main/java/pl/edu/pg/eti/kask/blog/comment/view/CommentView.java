package pl.edu.pg.eti.kask.blog.comment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.model.CommentModel;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;
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

    @Inject
    public CommentView(CommentService commentService, UserService userService, ArticleService articleService) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
    }

    /**
     * Initializes data
     *
     * @throws IOException thrown if any input/output exception
     */
    public void init() throws IOException {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()) {
            this.comment = CommentModel.convertFromEntity(comment.get(), userService, articleService);
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Comment not found");
        }
    }

}

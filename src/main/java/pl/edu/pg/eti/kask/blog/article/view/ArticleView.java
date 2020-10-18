package pl.edu.pg.eti.kask.blog.article.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.model.ArticleModel;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.model.CommentsModel;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * View bean for {@link Article}
 */
@Named
@SessionScoped
public class ArticleView implements Serializable {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    /**
     * Id of article, loaded from path param
     */
    @Getter
    @Setter
    private Long id;

    /**
     * Instance of article
     */
    @Getter
    private ArticleModel article;

    @Inject
    public ArticleView(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * Initializes article
     *
     * @throws IOException thrown if any input/output exception
     */
    public void init() throws IOException {
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            this.article = ArticleModel.convertFromEntity(article.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Article not found");
        }
    }

    /**
     * Searches for all comments for article
     *
     * @return list of comments
     */
    public List<CommentsModel> getComments() {
        return commentService.findAllByArticleId(id).stream()
                .map(c -> {
                    try {
                        return CommentsModel.convertFromEntity(c, userService);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .sorted(Comparator.comparing(CommentsModel::getCreationTime))
                .collect(Collectors.toList());
    }

    /**
     * Deletes comment
     * @return navigation to same site
     */
    public String deleteAction(CommentsModel comment) {
        commentService.delete(CommentsModel.convertToEntity(comment));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}

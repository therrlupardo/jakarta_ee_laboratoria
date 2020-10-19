package pl.edu.pg.eti.kask.blog.comment.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.model.ArticleModel;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.model.CommentModel;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;
import pl.edu.pg.eti.kask.blog.user.model.UserModel;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * View bean of {@link pl.edu.pg.eti.kask.blog.comment.entity.Comment}
 * Used while adding comments
 */
@Named
@NoArgsConstructor
@ConversationScoped
public class CommentAdd implements Serializable {
    private CommentService commentService;
    private UserService userService;
    private ArticleService articleService;

    /**
     * List of available users to select as author of comment
     */
    @Getter
    private List<UserModel> users;

    /**
     * List of all available articles
     */
    @Getter
    private List<ArticleModel> articles;

    /**
     * Model of comment
     */
    @Getter
    private CommentModel comment;

    /**
     * Injected conversation.
     */
    private Conversation conversation;

    @Inject
    public CommentAdd(CommentService commentService, UserService userService, ArticleService articleService, Conversation conversation) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
        this.conversation = conversation;
    }

    /**
     * Initializes view data. Starts conversation
     */
    @PostConstruct
    public void init() {
        users = userService.findAll().stream()
                .map(UserModel::convertFromEntity)
                .sorted(Comparator.comparing(UserModel::getId))
                .collect(Collectors.toList());
        articles = articleService.findAll().stream()
                .map(ArticleModel::convertFromEntity)
                .sorted(Comparator.comparing(ArticleModel::getId))
                .collect(Collectors.toList());
        comment = CommentModel.builder().build();
        conversation.begin();
    }

    /**
     * Adds comment
     *
     * @return navigation to article, to which comment was added
     */
    public String addCommentAction() {
        commentService.createComment(CommentModel.convertToEntity(comment));
        conversation.end();
        return "/articles/article_view.xhtml?id=" + comment.getArticle().getId() + "&faces-redirect=true&includeViewParams=true";

    }
}

package pl.edu.pg.eti.kask.blog.comment.model;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.model.ArticleModel;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.model.UserModel;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * View bean for {@link Comment}
 * Represents view of single comment
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CommentModel {

    /**
     * Unique comment identifier
     */
    private Long id;
    /**
     * Author of comment
     */
    private UserModel author;
    /**
     * Content of comment
     */
    private String content;
    /**
     * Article to which comment was added
     */
    private ArticleModel article;
    /**
     * Time of creation of comment
     */
    private LocalDateTime creationTime;
    /**
     * Number of likes under comment
     */
    private Long numberOfLikes;


    /**
     * Converts Comment to CommentModel
     *
     * @param comment        Comment to be converted
     * @param userService    needed to load user by it's id stored in Comment
     * @param articleService needed to load article by it's id stored in Comment
     * @return converted model
     * @throws IOException thrown if any input/output exception
     */
    public static CommentModel convertFromEntity(Comment comment, UserService userService, ArticleService articleService) throws IOException {
        Optional<Article> article = articleService.findById(comment.getArticleId());
        Optional<User> author = userService.findById(comment.getUserId());
        if (article.isPresent() && author.isPresent()) {
            return CommentModel.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .article(ArticleModel.convertFromEntity(article.get()))
                    .author(UserModel.convertFromEntity(author.get()))
                    .creationTime(comment.getCreationTime())
                    .numberOfLikes(comment.getNumberOfLikes())
                    .build();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
                    (article.isPresent() ? "Author" : "Article") + " doesn't exist");
            return null;
        }
    }

    /**
     * Converts CommentModel to Comment
     *
     * @param comment CommentModel to be converted
     * @return converted entity
     */
    public static Comment convertToEntity(CommentModel comment) {
        return Comment.builder()
                .id(comment.getId())
                .articleId(comment.getArticle().getId())
                .userId(comment.getAuthor().getId())
                .content(comment.getContent())
                .creationTime(comment.getCreationTime())
                .numberOfLikes(comment.getNumberOfLikes())
                .build();
    }
}

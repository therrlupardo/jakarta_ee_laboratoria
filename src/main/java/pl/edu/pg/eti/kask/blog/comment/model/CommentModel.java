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

    private Long id;
    private UserModel author;
    private String content;
    private ArticleModel article;
    private LocalDateTime creationTime;
    private Long numberOfLikes;


    public static CommentModel convertFromEntity(Comment comment, UserService userService, ArticleService articleService) throws IOException {
        System.out.println(comment.getArticleId() + " " + comment.getUserId());
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

package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.user.entity.User;

/**
 * @author mateusz.buchajewicz
 * <p>
 * POST comment request. Contains only fields required to create comment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommentRequest {
    /**
     * Content of comment
     */
    private String content;

    /**
     * Unique identifier of user which posted comment
     */
    private Long userId;

    /**
     * Unique identifier of article, to which comment was added to
     */
    private Long articleId;

    /**
     * Converts CreateCommentRequest to Comment
     *
     * @param request data of new comment
     * @param article article to which comment should be added
     * @return converted entity
     */
    public static Comment convertToEntity(CreateCommentRequest request, Article article) {
        return Comment.builder()
                .article(article)
                .content(request.getContent())
                .build();
    }
}

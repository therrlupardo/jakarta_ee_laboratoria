package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

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
     * @param request   data of new comment
     * @param articleId unique identifier of article, to which comment was added
     * @return converted entity
     */
    public static Comment convertToEntity(CreateCommentRequest request, Long articleId) {
        return Comment.builder()
                .userId(request.getUserId())
                .content(request.getContent())
                .articleId(articleId)
                .build();
    }
}

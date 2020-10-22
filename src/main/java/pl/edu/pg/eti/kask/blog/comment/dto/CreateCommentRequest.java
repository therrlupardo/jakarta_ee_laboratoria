package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

/**
 * @author mateusz.buchajewicz
 * <p>
 * POST comment request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommentRequest {
    private String content;
    private Long userId;
    private Long articleId;

    public static Comment convertToEntity(CreateCommentRequest request, Long articleId) {
        return Comment.builder()
                .userId(request.getUserId())
                .content(request.getContent())
                .articleId(articleId)
                .build();
    }
}

package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * <p>
 * GET comment response
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetCommentResponse {
    /**
     * Unique comment identifier
     */
    private Long id;

    /**
     * Identifier of author of comment
     */
    private Long authorId;

    /**
     * Identifier of article
     */
    private Long articleId;

    /**
     * Content of comment
     */
    private String content;

    /**
     * Time of creation of comment
     */
    private LocalDateTime creationTime;

    /**
     * Converts comment entity to response object
     *
     * @param comment entity of comment
     * @return converted object
     */
    public static GetCommentResponse convertFromEntity(Comment comment) {
        return GetCommentResponse.builder()
                .id(comment.getId())
                .authorId(comment.getUser().getId())
                .articleId(comment.getId())
                .content(comment.getContent())
                .creationTime(comment.getCreationTime())
                .build();
    }
}

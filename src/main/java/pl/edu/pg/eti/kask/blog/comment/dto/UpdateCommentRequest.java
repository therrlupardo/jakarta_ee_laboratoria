package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

/**
 * @author mateusz.buchajewicz
 * <p>
 * PUT comment request. Contains only fields available for edition
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCommentRequest {
    /**
     * Content of comment
     */
    private String content;

    /**
     * Converts UpdateCommentRequest to Comment
     *
     * @param comment earlier value of comment, needed to preserve not edited part of data
     * @param request object containing edited fields
     * @return converted comment
     */
    public static Comment convertToEntity(Comment comment, UpdateCommentRequest request) {
        comment.setContent(request.getContent());
        return comment;
    }
}

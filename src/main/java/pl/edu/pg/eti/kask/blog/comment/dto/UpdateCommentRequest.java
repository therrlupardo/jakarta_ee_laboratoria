package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCommentRequest {
    private String content;

    public static Comment convertToEntity(Comment comment, UpdateCommentRequest request) {
        comment.setContent(request.getContent());
        return comment;
    }
}

package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * DTO for {@link pl.edu.pg.eti.kask.blog.comment.entity.Comment}, view in list under articles
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
    private Long id;
    private String content;
    private Long authorId;
    private LocalDateTime creationTime;
    private Long numberOfLikes;

    public static CommentsDto mapFromEntity(Comment entity) {
        return CommentsDto.builder()
                .id(entity.getId())
                .authorId(entity.getUserId())
                .content(entity.getContent())
                .creationTime(entity.getCreationTime())
                .numberOfLikes(entity.getNumberOfLikes())
                .build();
    }

    public static Comment mapToEntity(CommentsDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .userId(dto.getAuthorId())
                .content(dto.getContent())
                .creationTime(dto.getCreationTime())
                .numberOfLikes(dto.getNumberOfLikes())
                .build();
    }
}

package pl.edu.pg.eti.kask.blog.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.user.model.UserModel;

import java.time.LocalDateTime;

/**
 * @author mateusz.bucahejewicz
 * JSF view of {@link Comment}, created to not use entity
 * Reporesents comment in list of comments
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsModel {

    /**
     * Unique comment identifier
     */
    private Long id;

    /**
     * Content of comment
     */
    private String content;

    /**
     * Author of comment
     */
    private UserModel author;

    /**
     * Time of creation of comment
     */
    private LocalDateTime creationTime;

    /**
     * Number of likes under comment
     */
    private Long numberOfLikes;

    /**
     * Converts Comment to CommentsModel
     *
     * @param entity      comment to be converted
     * @return converted model
     */
    public static CommentsModel convertFromEntity(Comment entity) {
        return CommentsModel.builder()
                .id(entity.getId())
                .author(UserModel.convertFromEntity(entity.getUser()))
                .content(entity.getContent())
                .creationTime(entity.getCreationTime())
                .numberOfLikes(entity.getNumberOfLikes())
                .build();
    }

    /**
     * Converts CommentsModel to Comment
     *
     * @param model model to be converted
     * @return converted entity
     */
    public static Comment convertToEntity(CommentsModel model) {
        return Comment.builder()
                .id(model.getId())
                .content(model.getContent())
                .creationTime(model.getCreationTime())
                .numberOfLikes(model.getNumberOfLikes())
                .build();
    }

}

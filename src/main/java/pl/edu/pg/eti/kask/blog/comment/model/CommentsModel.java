package pl.edu.pg.eti.kask.blog.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
     * @param userService needed to load user by it's id stored in Comment
     * @return converted model
     * @throws IOException thrown if any input/output exception
     */
    public static CommentsModel convertFromEntity(Comment entity, UserService userService) throws IOException {
        Optional<User> user = userService.findById(entity.getUserId());
        if (user.isPresent()) {
            return CommentsModel.builder()
                    .id(entity.getId())
                    .author(UserModel.convertFromEntity(user.get()))
                    .content(entity.getContent())
                    .creationTime(entity.getCreationTime())
                    .numberOfLikes(entity.getNumberOfLikes())
                    .build();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "User doesn't exist");
            return null;
        }
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

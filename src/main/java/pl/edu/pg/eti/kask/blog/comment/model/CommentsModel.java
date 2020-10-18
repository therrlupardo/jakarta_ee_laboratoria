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
    private Long id;
    private String content;
    private UserModel author;
    private LocalDateTime creationTime;
    private Long numberOfLikes;

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

    public static Comment convertToEntity(CommentsModel dto) {
        return Comment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .creationTime(dto.getCreationTime())
                .numberOfLikes(dto.getNumberOfLikes())
                .build();
    }

}

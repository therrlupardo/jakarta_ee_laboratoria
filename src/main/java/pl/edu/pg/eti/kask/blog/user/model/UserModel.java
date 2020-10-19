package pl.edu.pg.eti.kask.blog.user.model;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import java.time.LocalDate;
import java.util.List;

/**
 * JSF view model class in order to not to use entity classes.
 * Represents single user to be displayed
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserModel {

    /**
     * User's id
     */
    private Long id;

    /**
     * User's username
     */
    private String username;

    /**
     * User's password, hashed with {@link Sha256HashingUtility}
     */
    private String password;

    /**
     * User's date of birth
     */
    private LocalDate birthdate;

    /**
     * List of comments added by user
     */
    private List<Comment> comments;

    /**
     * Converts User to UserModel
     *
     * @param entity entity to be converted
     * @return converted model
     */
    public static UserModel convertFromEntity(User entity) {
        return UserModel.builder().id(entity.getId()).username(entity.getUsername()).build();
    }
}

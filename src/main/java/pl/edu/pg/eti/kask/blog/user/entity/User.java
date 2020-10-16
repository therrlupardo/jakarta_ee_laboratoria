package pl.edu.pg.eti.kask.blog.user.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 * Entity for system user
 * Represents information about particular user, including his credentials.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable {
    /**
     * User's id
     */
    private Long id;

    /**
     * User's username
     */
    private String username;

    /**
     * User's password, hashed with {@link pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility}
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

    private byte[] avatar;

    public String getAvatarFileName() {
        return id + ".png";
    }
}

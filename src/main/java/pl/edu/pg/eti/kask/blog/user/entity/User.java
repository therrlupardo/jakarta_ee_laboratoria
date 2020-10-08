package pl.edu.pg.eti.kask.blog.user.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

import java.time.LocalDate;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 * @since 08.10.2020
 * Entity for system user
 * Represents information about particular user, including his credentials.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class User {
    /**
     * User's id
     */
    private Long id;

    /**
     * User's username
     */
    private String username;

    /**
     * User's date of birth
     */
    private LocalDate birthdate;

    /**
     * List of comments added by user
     */
    private List<Comment> comments;
}

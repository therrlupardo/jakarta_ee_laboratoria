package pl.edu.pg.eti.kask.blog.comment.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.common.interfaces.Entity;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * Entity for comment.
 * Represents information about particular comment, article comment was added to and user which added comment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class Comment implements Entity, Serializable {
    /**
     * Comment's id
     */
    private Long id;

    /**
     * Comment's content
     */
    private String content;

    /**
     * Id of article, under which comment was added
     */
    private Long articleId;

    /**
     * Id of user which added comment
     */
    private Long userId;

    /**
     * Date and time, when article was created
     */
    private LocalDateTime creationTime;

    /**
     * Number of "likes" added to comment
     */
    private Long numberOfLikes;
}

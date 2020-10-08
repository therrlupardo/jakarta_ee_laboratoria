package pl.edu.pg.eti.kask.blog.comment.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * @since 08.10.2020
 * Entity for comment.
 * Represents information about particular comment, article comment was added to and user which added comment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class Comment {
    /**
     * Comment's id
     */
    private Long id;

    /**
     * Comment's content
     */
    private String content;

    /**
     * Article, to which comment was added
     */
    private Article article;

    /**
     * User which added comment
     */
    private User user;

    /**
     * Date and time, when article was created
     */
    private LocalDateTime creationTime;

    /**
     * Number of "likes" added to comment
     */
    private Long numberOfLikes;
}

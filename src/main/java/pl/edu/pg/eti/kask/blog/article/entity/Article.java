package pl.edu.pg.eti.kask.blog.article.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * @since 08.10.2020
 * Entity for article.
 * Describes it's title, author, content and creation date and time.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class Article {
    /**
     * Article's id
     */
    private Long id;

    /**
     * Article's title
     */
    private String title;

    /**
     * Article's author
     */
    private String author;

    /**
     * Article's content
     */
    private String content;

    /**
     * Article's creation date and time
     */
    private LocalDateTime creationTime;

    /**
     * Number of "likes" added to article
     */
    private Long numberOfLikes;
}

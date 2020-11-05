package pl.edu.pg.eti.kask.blog.comment.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * Entity for comment.
 * Represents information about particular comment, article comment was added to and user which added comment.
 */
@Data
@Entity
@Table(name = "comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment implements Serializable {
    /**
     * Comment's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Comment's content
     */
    private String content;

    /**
     * Date and time, when article was created
     */
    private LocalDateTime creationTime;

    /**
     * Number of "likes" added to comment
     */
    private Long numberOfLikes;

    /**
     * Represents of article, to which comment was added
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    /**
     * Represents user that created comment
     */
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}

package pl.edu.pg.eti.kask.blog.comment.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

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

            @EqualsAndHashCode.Exclude
            @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article")
    private Article article;

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

package pl.edu.pg.eti.kask.blog.article.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 * Entity for article.
 * Describes it's title, author, content and creation date and time.
 */
@Data
@Entity
@Table(name = "article")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Article implements Serializable {
    /**
     * Article's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    /**
     * List of comments
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Comment> comments;
}

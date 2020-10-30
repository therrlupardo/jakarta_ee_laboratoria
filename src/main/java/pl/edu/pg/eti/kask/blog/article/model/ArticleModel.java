package pl.edu.pg.eti.kask.blog.article.model;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * JSF view of {@link Article}, used to not use entity
 * Represents single article
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleModel {

    /**
     * Unique article identifier
     */
    private Long id;
    /**
     * Title of article
     */
    private String title;
    /**
     * Author of article
     */
    private String author;
    /**
     * Time of creation of article
     */
    private LocalDateTime creationTime;
    /**
     * Number of likes from users
     */
    private Long numberOfLikes;
    /**
     * Main content of article
     */
    private String content;

    /**
     * Converts Article to ArticleModel
     *
     * @param entity entity of article to be converted
     * @return entity converted to ArticleModel
     */
    public static ArticleModel convertFromEntity(Article entity) {
        return ArticleModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .creationTime(entity.getCreationTime())
                .numberOfLikes(entity.getNumberOfLikes())
                .content(entity.getContent())
                .build();
    }

    /**
     * Converts ArticleModel to Article
     *
     * @param article entity of article
     * @return converted object
     */
    public static Article convertToEntity(ArticleModel article) {
        return Article.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .creationTime(article.getCreationTime())
                .numberOfLikes(article.getNumberOfLikes())
                .content(article.getContent())
                .build();
    }
}

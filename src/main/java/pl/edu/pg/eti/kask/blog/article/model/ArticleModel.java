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

    private Long id;
    private String title;
    private String author;
    private LocalDateTime creationTime;
    private Long numberOfLikes;
    private String content;

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

    public static Article convertToEntity(ArticleModel dto) {
        return Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .creationTime(dto.getCreationTime())
                .numberOfLikes(dto.getNumberOfLikes())
                .content(dto.getContent())
                .build();
    }
}

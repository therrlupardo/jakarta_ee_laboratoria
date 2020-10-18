package pl.edu.pg.eti.kask.blog.article.model;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

/**
 * @author mateusz.buchajewicz
 * JSF view of {@link Article}, created to not use entity
 * Reporesents article on list of articles
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticlesModel {
    private Long id;
    private String title;
    private String author;

    public static ArticlesModel convertFromEntity(Article entity) {
        return ArticlesModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .build();
    }

    public static Article convertToEntity(ArticlesModel dto) {
        return Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .build();
    }
}

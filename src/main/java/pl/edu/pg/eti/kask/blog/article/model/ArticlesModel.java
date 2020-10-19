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
    /**
     * Unique identifier of Article
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
     * Converts Article to ArticlesModel
     *
     * @param entity entity of Article to be converted
     * @return entity converted to ArticlesModel
     */
    public static ArticlesModel convertFromEntity(Article entity) {
        return ArticlesModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .build();
    }

    /**
     * Converts ArticlesModel to Article
     *
     * @param model object of ArticlesModel to be converted
     * @return object converted to entity Article
     */
    public static Article convertToEntity(ArticlesModel model) {
        return Article.builder()
                .id(model.getId())
                .title(model.getTitle())
                .author(model.getAuthor())
                .build();
    }
}

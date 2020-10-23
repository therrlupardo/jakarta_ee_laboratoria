package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

/**
 * @author mateusz.buchajewicz
 * <p>
 * PUT article requests. Contains only fields available to edition from {@link Article}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateArticleRequest {
    /**
     * Content of article
     */
    private String content;

    /**
     * Converts UpdateArticleRequest to Article
     *
     * @param article initial value of article, containing values of other fields
     * @param request object with fields to be updated in article
     * @return article updated with data from request
     */
    public static Article convertToEntity(Article article, UpdateArticleRequest request) {
        article.setContent(request.getContent());
        return article;
    }
}

package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;

import java.util.List;

/**
 * @author mateusz.buchajewicz
 *
 * GET articles response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetArticlesResponse {

    /**
     * List of articles
     */
    @Singular
    private List<Article> articles;

    /**
     * Converts list of article entities to response object
     * @param articles list of entities
     * @return converted object
     */
    public static GetArticlesResponse convertFromEntities(List<pl.edu.pg.eti.kask.blog.article.entity.Article> articles) {
        GetArticlesResponseBuilder response = GetArticlesResponse.builder();
        articles.stream().map(a -> Article.builder()
                .id(a.getId())
                .title(a.getTitle())
                .build())
                .forEach(response::article);
        return response.build();
    }

    /**
     * Class which represent element of list in response
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Article {
        /**
         * Unique article identifier
         */
        private Long id;

        /**
         * Title of article
         */
        private String title;
    }
}

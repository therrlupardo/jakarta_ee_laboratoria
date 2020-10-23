package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

/**
 * @author mateusz.buchajewicz
 * <p>
 * POST character request, has only fields required to create user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateArticleRequest {
    /**
     * Title of article
     */
    private String title;

    /**
     * Author of article
     */
    private String author;

    /**
     * Content of article
     */
    private String content;

    /**
     * Converts CreateArticleRequest to Article
     *
     * @param request object to be converted
     * @return converted entity
     */
    public static Article convertToEntity(CreateArticleRequest request) {
        return Article.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .content(request.getContent())
                .build();
    }
}

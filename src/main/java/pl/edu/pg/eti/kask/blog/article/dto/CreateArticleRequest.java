package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

/**
 * @author mateusz.buchajewicz
 *
 * POST character request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateArticleRequest {
    private String title;
    private String author;
    private String content;

    public static Article mapToEntity(CreateArticleRequest request) {
        return Article.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .content(request.getContent())
                .build();
    }
}

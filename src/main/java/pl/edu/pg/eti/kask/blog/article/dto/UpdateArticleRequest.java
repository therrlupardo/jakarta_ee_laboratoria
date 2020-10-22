package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 *
 * PUT article requests. Contains only fields available to edition from {@link Article}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateArticleRequest {
    private String content;

    public static Article convertToEntity(Article article, UpdateArticleRequest request) {
        article.setContent(request.getContent());
        return article;
    }
}

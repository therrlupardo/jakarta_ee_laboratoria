package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

/**
 * @author mateusz.buchajewicz
 * DTO for {@link pl.edu.pg.eti.kask.blog.article.entity.Article} class to display on list of articles
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticlesDto {
    private Long id;
    private String title;
    private String author;

    public static ArticlesDto mapFromEntity(Article entity) {
        return ArticlesDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .build();
    }

    public static Article mapToEntity(ArticlesDto dto) {
        return Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .build();
    }
}

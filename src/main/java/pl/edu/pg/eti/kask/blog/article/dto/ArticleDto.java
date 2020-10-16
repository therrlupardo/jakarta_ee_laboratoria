package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;

import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * DTO for {@link pl.edu.pg.eti.kask.blog.article.entity.Article} class, for article view
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime creationTime;
    private Long numberOfLikes;
    private String content;

    public static ArticleDto mapFromEntity(Article entity) {
        return ArticleDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .creationTime(entity.getCreationTime())
                .numberOfLikes(entity.getNumberOfLikes())
                .content(entity.getContent())
                .build();
    }

    public static Article mapToEntity(ArticleDto dto) {
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

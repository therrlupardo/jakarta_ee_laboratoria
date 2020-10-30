package pl.edu.pg.eti.kask.blog.article.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.comment.dto.GetCommentsResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 *
 * GET article response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetArticleResponse {
    /**
     * Unique identifier of article
     */
    private Long id;

    /**
     * Title of article
     */
    private String title;

    /**
     * Content of article
     */
    private String content;

    /**
     * Author of article
     */
    private String author;

    /**
     * Time of creation of article
     */
    private LocalDateTime creationTime;

    /**
     * List of comments of article, shown as in {@link GetCommentsResponse.Comment}
     */
    private List<GetCommentsResponse.Comment> comments;

    /**
     * Converts entity of article to response
     *
     * @param article entity of article
     * @return converted response
     */
    public static GetArticleResponse convertFromEntity(Article article) {
        return GetArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .creationTime(article.getCreationTime())
                .comments(GetCommentsResponse.convertFromEntities(article.getComments()).getComments())
                .build();
    }
}

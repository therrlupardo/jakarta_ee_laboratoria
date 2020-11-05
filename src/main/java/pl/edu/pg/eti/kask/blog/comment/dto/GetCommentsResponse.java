package pl.edu.pg.eti.kask.blog.comment.dto;

import lombok.*;

import java.util.List;

/**
 * @author mateusz.buchajewicz
 * <p>
 * GET comments response. Contains list of comments of given article
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsResponse {

    /**
     * List of comments
     */
    @Singular
    private List<Comment> comments;

    /**
     * Converts list of comment entities to response object
     *
     * @param comments list of entities
     * @return response object
     */
    public static GetCommentsResponse convertFromEntities(List<pl.edu.pg.eti.kask.blog.comment.entity.Comment> comments) {
        GetCommentsResponseBuilder response = GetCommentsResponse.builder();
        comments.stream().map(c -> Comment.builder()
                .id(c.getId())
                .content(c.getContent())
                .authorId(c.getUser().getId())
                .build())
                .forEach(response::comment);
        return response.build();
    }

    /**
     * Class which represent element of list of comments in response
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Comment {
        /**
         * Unique comment identifier
         */
        private Long id;

        /**
         * Content of comment
         */
        private String content;

        /**
         * Identifier of author of comment
         */
        private Long authorId;
    }
}

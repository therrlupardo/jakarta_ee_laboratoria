package pl.edu.pg.eti.kask.blog.comment.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.dto.CreateCommentRequest;
import pl.edu.pg.eti.kask.blog.comment.dto.GetCommentResponse;
import pl.edu.pg.eti.kask.blog.comment.dto.GetCommentsResponse;
import pl.edu.pg.eti.kask.blog.comment.dto.UpdateCommentRequest;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * <p>
 * REST controller for {@link Comment}
 */
@Path("articles/{articleId}/comments")
@NoArgsConstructor
public class CommentController {
    private CommentService commentService;
    private ArticleService articleService;
    private UserService userService;

    @Inject
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Inject
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Inject
    public void setUserService(UserService userService) { this.userService = userService; }

    /**
     * Searches for all comments added to article
     *
     * @param articleId unique identifier of {@link Article}
     * @return list of comments from article
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("articleId") Long articleId) {
        Optional<Article> article = articleService.findById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(GetCommentsResponse.convertFromEntities(commentService.findAllByArticleId(articleId))).build();
    }

    /**
     * Searches for comment with given commentId, from article with articleId
     *
     * @param articleId unique identifier of article
     * @param commentId unique identifier of comment
     * @return Response(200) with comment as request body, Response(404) if either comment or article with given id exist
     */
    @GET
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentById(@PathParam("articleId") Long articleId, @PathParam("commentId") Long commentId) {
        Optional<Comment> comment = commentService.findOneByArticleId(articleId, commentId);
        if (comment.isPresent()) {
            return Response.ok(GetCommentResponse.convertFromEntity(comment.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Creates comment under article with given id
     *
     * @param articleId unique article identifier
     * @param request   data of comment to be added
     * @return Response(201) if added correctly, Response(404) if article doesn't exist
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("articleId") Long articleId, CreateCommentRequest request) {
        Optional<Article> article = articleService.findById(articleId);
        Optional<User> user = userService.findById(request.getUserId());
        if (article.isPresent() && user.isPresent()) {
            Comment comment = CreateCommentRequest.convertToEntity(request, article.get(), user.get());
            commentService.createComment(comment);
            URI getCommentByIdUri = URI.create(
                    UriBuilder.fromResource(CommentController.class).build(articleId).getPath() +
                            UriBuilder.fromMethod(CommentController.class, "getCommentById").build(comment.getId())
            );
            return Response.created(getCommentByIdUri).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Updates comment with given commentId, from article with articleId
     *
     * @param articleId unique identifier of article
     * @param commentId unique identifier of comment to be edited
     * @param request   object with values which should be inserted to edited object
     * @return Response(200) if update successful, Response(404) if either article or comment doesn't exist
     */
    @PUT
    @Path("{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(@PathParam("articleId") Long articleId, @PathParam("commentId") Long commentId, UpdateCommentRequest request) {
        Optional<Comment> comment = commentService.findOneByArticleId(articleId, commentId);
        if (comment.isPresent()) {
            commentService.updateComment(commentId, UpdateCommentRequest.convertToEntity(comment.get(), request));
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Deletes comment with given commentId, from article with given articleId
     *
     * @param articleId unique identifier of article
     * @param commentId unique identifier of comment to be deleted
     * @return Response(200) is delete successful, Response(404) if either article or comment doesn't exist
     */
    @DELETE
    @Path("{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("articleId") Long articleId, @PathParam("commentId") Long commentId) {
        Optional<Comment> comment = commentService.findOneByArticleId(articleId, commentId);
        if (comment.isPresent()) {
            commentService.delete(comment.get());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}

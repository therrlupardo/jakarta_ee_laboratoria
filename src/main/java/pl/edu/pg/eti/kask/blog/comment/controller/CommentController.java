package pl.edu.pg.eti.kask.blog.comment.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.dto.CreateCommentRequest;
import pl.edu.pg.eti.kask.blog.comment.dto.UpdateCommentRequest;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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

    @Inject
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Inject
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("articleId") Long articleId) {
        return Response.ok(commentService.findAllByArticleId(articleId)).build();
    }

    @GET
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentById(@PathParam("articleId") Long articleId, @PathParam("commentId") Long commentId) {
        Optional<Comment> comment = commentService.findOneByArticleId(articleId, commentId);
        if (comment.isPresent()) {
            return Response.ok(comment.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@PathParam("articleId") Long articleId, CreateCommentRequest request) {
        Optional<Article> article = articleService.findById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Comment comment = CreateCommentRequest.convertToEntity(request, articleId);
        commentService.createComment(comment);
        return Response.created(
                UriBuilder.fromMethod(CommentController.class, "getCommentById")
                        .build(articleId, request.getUserId())
        ).build();
    }

    @PUT
    @Path("{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateComment(@PathParam("articleId") Long articleId, @PathParam("commentId") Long commentId, UpdateCommentRequest request) {
        Optional<Article> article = articleService.findById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Optional<Comment> comment = commentService.findOneByArticleId(articleId, commentId);
        if (comment.isPresent()) {
            commentService.updateComment(commentId, UpdateCommentRequest.convertToEntity(comment.get(), request));
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("articleId") Long articleId, @PathParam("commentId") Long commentId) {
        Optional<Comment> comment = commentService.findOneByArticleId(articleId, commentId);
        if (comment.isPresent()) {
            commentService.delete(comment.get());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}

package pl.edu.pg.eti.kask.blog.article.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.dto.CreateArticleRequest;
import pl.edu.pg.eti.kask.blog.article.dto.UpdateArticleRequest;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 *
 * REST controller for articles0
 */
@Path("/articles")
@NoArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @Inject
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticles() {
        return Response.ok(this.articleService.findAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleById(@PathParam("id") Long id) {
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            return Response.ok(article.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArticle(CreateArticleRequest request) {
        if (request == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        Article article = CreateArticleRequest.mapToEntity(request);
        articleService.createArticle(article);
        return Response.created(
                UriBuilder.fromMethod(ArticleController.class, "getArticleById")
                .build(article.getId())
        ).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateArticle(@PathParam("id") Long id, UpdateArticleRequest request) {
        if (request == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            articleService.updateArticle(id, UpdateArticleRequest.convertToEntity(article.get(), request));
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArticle(@PathParam("id") Long id) {
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            articleService.delete(article.get());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

package pl.edu.pg.eti.kask.blog.article.controller;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.dto.CreateArticleRequest;
import pl.edu.pg.eti.kask.blog.article.dto.UpdateArticleRequest;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;

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
 * REST controller for {@link Article}
 */
@Path("/articles")
@NoArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @Inject
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Searches for all articles
     *
     * @return list of all articles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticles() {
        return Response.ok(this.articleService.findAll()).build();
    }

    /**
     * Searches for article with given id
     *
     * @param id id of article to be found
     * @return Response(200) with article in response body or Response(404) if article doesn't exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleById(@PathParam("id") Long id) {
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            return Response.ok(article.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Creates new article
     *
     * @param request HTTP request, contains data of Article to be created
     * @return Response(201 CREATED) after creation of article
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArticle(CreateArticleRequest request) {
        Article article = CreateArticleRequest.convertToEntity(request);
        articleService.create(article);

        URI getArticleByIdUri = URI.create(
                UriBuilder.fromResource(ArticleController.class).build().getPath() +
                        UriBuilder.fromMethod(ArticleController.class, "getArticleById").build(article.getId())
        );

        return Response.created(getArticleByIdUri).build();
    }

    /**
     * Updates article with given id
     *
     * @param id      id of article to be updated
     * @param request HTTP request, with data of article, which should be updated
     * @return Response(200) if article was successfully updated, Response(404) if article with id doesn't exist
     */
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateArticle(@PathParam("id") Long id, UpdateArticleRequest request) {
        Optional<Article> article = articleService.findById(id);
        if (article.isPresent()) {
            articleService.update(id, UpdateArticleRequest.convertToEntity(article.get(), request));
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Deletes article with given id
     *
     * @param id id of article to be deleted
     * @return Response(200) is article was deleted, Response(404) if no article with given id
     */
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

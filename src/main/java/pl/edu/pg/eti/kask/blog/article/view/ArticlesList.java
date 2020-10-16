package pl.edu.pg.eti.kask.blog.article.view;

import pl.edu.pg.eti.kask.blog.article.dto.ArticlesDto;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * View bean for list of {@link pl.edu.pg.eti.kask.blog.article.entity.Article}
 */
@Named
@RequestScoped
public class ArticlesList implements Serializable {

    private final ArticleService articleService;
    private final CommentService commentService;

    @Inject
    public ArticlesList(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    /**
     * Searches for all articles
     * @return list of all articles as {@link ArticlesDto}
     */
    public List<ArticlesDto> getArticles() {
        return articleService.findAll().stream()
                .map(ArticlesDto::mapFromEntity)
                .sorted(Comparator.comparing(ArticlesDto::getId))
                .collect(Collectors.toList());
    }

    /**
     * Deletes specified article
     * @param articlesDto article to be deleted
     * @return navigation to same page
     */
    public String deleteAction(ArticlesDto articlesDto) {
        articleService.delete(ArticlesDto.mapToEntity(articlesDto));
        System.out.println(commentService.findAllByArticleId(articlesDto.getId()).size());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}

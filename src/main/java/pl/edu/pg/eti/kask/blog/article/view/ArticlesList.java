package pl.edu.pg.eti.kask.blog.article.view;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.model.ArticlesModel;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;

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
 * View bean for list of {@link Article}
 */
@Named
@RequestScoped
@NoArgsConstructor
public class ArticlesList implements Serializable {

    private ArticleService articleService;

    @Inject
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Searches for all articles
     *
     * @return list of all articles as {@link ArticlesModel}
     */
    public List<ArticlesModel> getArticles() {
        return articleService.findAll().stream()
                .map(ArticlesModel::convertFromEntity)
                .sorted(Comparator.comparing(ArticlesModel::getId))
                .collect(Collectors.toList());
    }

    /**
     * Deletes specified article
     *
     * @param articlesModel article to be deleted
     * @return navigation to same page
     */
    public String deleteAction(ArticlesModel articlesModel) {
        articleService.delete(ArticlesModel.convertToEntity(articlesModel));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}

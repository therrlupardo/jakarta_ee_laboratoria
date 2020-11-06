package pl.edu.pg.eti.kask.blog.article.model.converter;

import pl.edu.pg.eti.kask.blog.article.model.ArticleModel;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author mateusz.buchajewicz
 * Converter for {@link ArticleModel}
 */
@ManagedBean
@RequestScoped
@FacesConverter(forClass = ArticleModel.class, managed = true)
public class ArticleConverter implements Converter<ArticleModel> {

    private ArticleService articleService;

    @EJB
    public void setArticleService(ArticleService articleService) { this.articleService = articleService; }

    @Override
    public ArticleModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return articleService.findAll()
                .stream()
                .filter(a -> a.getTitle().equals(s))
                .findFirst()
                .map(ArticleModel::convertFromEntity)
                .orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, ArticleModel articleModel) {
        return articleModel == null ? "" : articleModel.getTitle();
    }
}

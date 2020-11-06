package pl.edu.pg.eti.kask.blog.article.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.repository.ArticleRepository;
import pl.edu.pg.eti.kask.blog.user.entity.UserRoles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for article entity
 */
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed(UserRoles.USER)
public class ArticleService implements Serializable {

    private ArticleRepository articleRepository;

    @Inject
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Searches for all articles
     *
     * @return list of all articles
     */
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    /**
     * Creates article
     *
     * @param article article to be created
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void create(Article article) {

        articleRepository.create(article);
    }

    /**
     * Deletes specified article
     *
     * @param article article to be deleted
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    /**
     * Searches for article with given id
     *
     * @param id id of article to be found
     * @return article with matching id as optional (can by empty)
     */
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    /**
     * Updates article with given id
     *
     * @param id      id of article to be updated
     * @param article data of article after update
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void update(Long id, Article article) {
        articleRepository.update(id, article);
    }
}

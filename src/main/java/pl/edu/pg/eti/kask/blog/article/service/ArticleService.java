package pl.edu.pg.eti.kask.blog.article.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.repository.ArticleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Service for article entity
 */
@NoArgsConstructor
@ApplicationScoped
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
    @Transactional
    public void create(Article article) {
        articleRepository.create(article);
    }

    /**
     * Deletes specified article
     *
     * @param article article to be deleted
     */
    @Transactional
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
    @Transactional
    public void update(Long id, Article article) {
        articleRepository.update(id, article);
    }
}

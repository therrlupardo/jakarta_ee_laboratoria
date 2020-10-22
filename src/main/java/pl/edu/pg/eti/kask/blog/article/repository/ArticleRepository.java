package pl.edu.pg.eti.kask.blog.article.repository;

import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;
import pl.edu.pg.eti.kask.blog.datastore.DataStore;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for Article entity
 */
@Dependent
public class ArticleRepository implements CrudRepository<Article> {
    private final DataStore dataStore;

    @Inject
    public ArticleRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Searches for article with given id
     *
     * @param id unique article identifier
     * @return article with matching id as optional (can be empty)
     */
    @Override
    public Optional<Article> findById(Long id) {
        return dataStore.findArticleById(id);
    }

    /**
     * Searches for all articles
     *
     * @return list of all articles
     */
    @Override
    public List<Article> findAll() {
        return dataStore.findAllArticles();
    }

    /**
     * Creates article
     *
     * @param article Article to be created
     */
    @Override
    public void create(Article article) {
        dataStore.createArticle(article);
    }

    /**
     * Deletes specified article
     *
     * @param article article to be removed
     */
    @Override
    public void delete(Article article) {
        dataStore.deleteArticle(article);
    }

    /**
     * Updates article with given id
     *
     * @param id      id of article to be updated
     * @param article data of article after modification
     */
    @Override
    public void update(Long id, Article article) {
        dataStore.updateArticle(id, article);
    }
}

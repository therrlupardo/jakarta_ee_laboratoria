package pl.edu.pg.eti.kask.blog.datastore;

import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.datastore.utils.DataStoreUtils;
import pl.edu.pg.eti.kask.blog.utils.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mateusz.buchajewicz
 * <p>
 * Data store for {@link Article}
 */
@ApplicationScoped
public class ArticleDataStore {

    /**
     * Set of all articles
     */
    private final Set<Article> articles = new HashSet<>();

    /**
     * Searches for all articles
     *
     * @return list of all articles
     */
    public synchronized List<Article> findAll() {
        List<Article> articleList = new ArrayList<>(articles);
        return articleList.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    /**
     * Adds article to dataStore, assigning new id to it
     *
     * @param article article to be added to DataStore
     */
    public synchronized void create(Article article) {
        article.setId(DataStoreUtils.getNextId(findAll()));
        articles.add(article);
    }


    /**
     * Deletes article from DataStore
     *
     * @param article article to be deleted
     */
    public synchronized void delete(Article article) {
        articles.removeIf(a -> a.getId().equals(article.getId()));
    }

    /**
     * Searches for article with given id
     *
     * @param id id of article to be found
     * @return article with matching id as optional (can be empty)
     */
    public Optional<Article> findById(Long id) {
        List<Article> article = articles.stream().filter(u -> u.getId().equals(id)).collect(Collectors.toList());
        return article.stream().findFirst().map(CloningUtility::clone);
    }

    /**
     * Updates article with given id
     *
     * @param id      id of article to be updated
     * @param article data of article after update
     */
    public void update(Long id, Article article) {
        articles.removeIf(a -> a.getId().equals(id));
        articles.add(article);
    }
}

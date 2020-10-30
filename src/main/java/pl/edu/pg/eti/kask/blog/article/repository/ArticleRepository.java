package pl.edu.pg.eti.kask.blog.article.repository;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.common.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author mateusz.buchajewicz
 * Repository for Article entity
 */
@Log
@RequestScoped
public class ArticleRepository implements CrudRepository<Article> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) { this.em = em; }


    /**
     * Searches for article with given id
     *
     * @param id unique article identifier
     * @return article with matching id as optional (can be empty)
     */
    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    /**
     * Searches for all articles
     *
     * @return list of all articles
     */
    @Override
    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    /**
     * Creates article
     *
     * @param article Article to be created
     */
    @Override
    public void create(Article article) {
        em.persist(article);
    }

    /**
     * Deletes specified article
     *
     * @param article article to be removed
     */
    @Override
    public void delete(Article article) {
        em.remove(em.find(Article.class, article.getId()));
    }

    /**
     * Updates article with given id
     *
     * @param id      id of article to be updated
     * @param article data of article after modification
     */
    @Override
    public void update(Long id, Article article) {
        em.merge(article);
    }
}

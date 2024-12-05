package org.odc.Repository;

import jakarta.persistence.EntityManager;
import org.odc.Entity.Article;
import jakarta.persistence.TypedQuery;
import org.odc.Entity.Compte;

import java.util.List;

public class ArticleRepositoryDb extends RepositoryBd<Article> implements ArticleRepository {
    private EntityManager em; // Déclarez la variable d'instance

    public ArticleRepositoryDb(EntityManager em) {
        super(Article.class, em); // Ajoutez em comme deuxième argument ici
        this.em = em; // Cela est maintenant redondant, car vous l'avez déjà passé au constructeur parent
    }


    @Override
    public List<Article> findByName(String nom) {
        TypedQuery<Article> query = em.createQuery(
                "SELECT a FROM Article a WHERE LOWER(a.nom) = LOWER(:nom)", Article.class);
        query.setParameter("nom", nom);
        return query.getResultList();
    }

    @Override
    public void updateStock(Long id, int quantity) {
        em.getTransaction().begin();
        Article article = em.find(Article.class, id);
        if (article != null) {
            article.setQte(article.getQte() - quantity);
        }
        em.getTransaction().commit();
    }
}
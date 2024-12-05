package org.odc.Repository;

import org.odc.Entity.Dette;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class DetteRepositoryDb extends RepositoryBd<Dette> implements DetteRepository {
    private EntityManager em; // Déclarez la variable d'instance

    public DetteRepositoryDb(EntityManager em) {
        super(Dette.class, em); // Ajoutez em comme deuxième argument ici
        this.em = em; // Cela est maintenant redondant, car vous l'avez déjà passé au constructeur parent
    }


    @Override
    public List<Dette> findByDate(Date date) {
        String jpql = "SELECT DISTINCT d FROM Dette d " +
                "JOIN FETCH d.articles a " +
                "JOIN FETCH a.article " +
                "WHERE DATE(d.dateCreation) = :date";

        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    public Dette findByIdWithDetails(Long id) {
        String jpql = "SELECT DISTINCT d FROM Dette d " +
                "LEFT JOIN FETCH d.articles a " +
                "LEFT JOIN FETCH a.article " +
                "LEFT JOIN FETCH d.client " +
                "WHERE d.id = :id";

        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}

    package org.odc.Repository;

import jakarta.persistence.EntityManager;
import org.odc.Entity.Paiement;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PaiementRepositoryDb extends RepositoryBd<Paiement> implements PaiementRepository {

    private EntityManager em; // Déclarez la variable d'instance

    public PaiementRepositoryDb(EntityManager em) {
        super(Paiement.class, em); // Ajoutez em comme deuxième argument ici
        this.em = em; // Cela est maintenant redondant, car vous l'avez déjà passé au constructeur parent
    }

    @Override
    public List<Paiement> findByDetteId(Long detteId) {
        String jpql = "SELECT p FROM Paiement p " +
                "LEFT JOIN FETCH p.dette d " +
                "WHERE d.id = :detteId " +
                "ORDER BY p.datePaiement DESC";
        TypedQuery<Paiement> query = em.createQuery(jpql, Paiement.class);
        query.setParameter("detteId", detteId);
        return query.getResultList();
    }
    // Other methods...


}
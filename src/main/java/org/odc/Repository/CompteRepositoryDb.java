package org.odc.Repository;

import jakarta.persistence.EntityManager;
import org.odc.Entity.Compte;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class CompteRepositoryDb extends RepositoryBd<Compte> implements CompteRepository {

    private EntityManager em; // Déclarez la variable d'instance

    public CompteRepositoryDb(EntityManager em) {
        super(Compte.class, em); // Ajoutez em comme deuxième argument ici
        this.em = em; // Cela est maintenant redondant, car vous l'avez déjà passé au constructeur parent
    }



    @Override
    public List<Compte> findByClientId(Long clientId) {
        String jpql = "SELECT c FROM Compte c WHERE c.clientId = :clientId";
        TypedQuery<Compte> query = em.createQuery(jpql, Compte.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    @Override
    public void delete(Long id) {
        Optional<Compte> compte = findById(id);
        compte.ifPresent(this::delete);
    }

    // Les autres méthodes (save, findById, findAll, delete(entity)) sont héritées de RepositoryBd
}
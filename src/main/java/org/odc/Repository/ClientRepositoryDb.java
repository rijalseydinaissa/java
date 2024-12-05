package org.odc.Repository;

import jakarta.persistence.EntityManager;
import org.odc.Entity.Client;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClientRepositoryDb extends RepositoryBd<Client> implements ClientRepository {
    private EntityManager em; // Déclarez la variable d'instance

    public ClientRepositoryDb(EntityManager em) {
        super(Client.class, em); // Ajoutez em comme deuxième argument ici
        this.em = em; // Cela est maintenant redondant, car vous l'avez déjà passé au constructeur parent
    }


    @Override
    public List<Client> findByName(String name) {
        String jpql = "SELECT c FROM Client c WHERE LOWER(c.nom) = LOWER(:name) OR LOWER(c.prenom) = LOWER(:name)";
        TypedQuery<Client> query = em.createQuery(jpql, Client.class);
        query.setParameter("name", name.toLowerCase());
        return query.getResultList();
    }
}
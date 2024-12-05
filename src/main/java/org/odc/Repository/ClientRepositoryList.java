package org.odc.Repository;

import jakarta.persistence.EntityManager;
import org.odc.Entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryList extends RepositoryList<Client> implements ClientRepository {
    private List<Client> clients;

    public ClientRepositoryList() {
        this.clients = new ArrayList<>();
    }
    @Override
    public List<Client> findByName(String name) {
        return entities.stream()
                .filter(c -> c.getNom().equalsIgnoreCase(name) || c.getPrenom().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }
}
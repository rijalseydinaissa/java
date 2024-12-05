package org.odc.Repository;

import org.odc.Entity.Compte;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompteRepositoryList extends RepositoryList<Compte> implements CompteRepository {
    private List<Compte> comptes;

    public CompteRepositoryList() {
        this.comptes = new ArrayList<>();
    }
    @Override
    public List<Compte> findByClientId(Long clientId) {
        return entities.stream()
                .filter(c -> c.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }
}
package org.odc.Repository;

import org.odc.Entity.Dette;
import org.odc.Entity.Paiement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class PaiementRepositoryList extends RepositoryList<Paiement> implements PaiementRepository {
    private List<Paiement> paiements;

    public PaiementRepositoryList() {
        this.paiements = new ArrayList<>();
    }
    @Override
    public List<Paiement> findByDetteId(Long detteId) {
        return paiements.stream()
                .filter(p -> p.getDette().getId().equals(detteId))
                .collect(Collectors.toList());
    }
}

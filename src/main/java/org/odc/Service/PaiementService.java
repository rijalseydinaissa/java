package org.odc.Service;

import org.odc.Entity.Paiement;
import java.util.List;
import java.util.Optional;

public interface PaiementService {
    Paiement createPaiement(Paiement paiement);
    List<Paiement> getAllPaiements();
    Optional<Optional<Paiement>> getPaiementById(Long id);
    void updatePaiement(Paiement paiement);
    void deletePaiement(Long id);
    List<Paiement> getPaiementsByDetteId(Long detteId);
}
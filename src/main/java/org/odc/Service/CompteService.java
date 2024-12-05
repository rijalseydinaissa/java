package org.odc.Service;

import org.odc.Entity.Compte;
import java.util.List;
import java.util.Optional;

public interface CompteService {
    Compte createCompte(Compte compte);
    List<Compte> getCompteList();
    Optional<Compte> getCompteById(long id);
    List<Compte> getComptesByClientId(long clientId);
}
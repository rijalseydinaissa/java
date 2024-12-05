package org.odc.Repository;

import org.odc.Entity.Compte;
import java.util.List;

public interface CompteRepository extends Repository<Compte> {
    List<Compte> findByClientId(Long clientId);
}
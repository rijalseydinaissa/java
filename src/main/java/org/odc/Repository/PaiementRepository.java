package org.odc.Repository;

import org.odc.Entity.Article;
import org.odc.Entity.Paiement;
import org.odc.Entity.Dette;
import java.util.List;
import java.util.Optional;


public interface PaiementRepository  extends Repository <Paiement> {
    List<Paiement> findByDetteId(Long detteId);

}

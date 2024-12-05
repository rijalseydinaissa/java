package org.odc.Repository;

import org.odc.Entity.Client;
import org.odc.Entity.Dette;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DetteRepositoryList extends RepositoryList<Dette> implements DetteRepository {
    private List<Dette> dettes;

    public DetteRepositoryList() {
        this.dettes = new ArrayList<>();
    }
    @Override
    public List<Dette> findByDate(Date date) {
        // Retourne une nouvelle liste (vide pour l'instant)
        return new ArrayList<>();
    }
}

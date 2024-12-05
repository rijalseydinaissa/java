package org.odc.Service;

import org.odc.Entity.Dette;
import java.util.List;
import java.util.Optional;

public interface DetteService {
    Dette createDette(Dette dette);
    List<Dette> getAllDettes();
    Optional<Dette> getDetteById(Long id);
    void updateDette(Dette dette);
    void deleteDette(Long id);
}
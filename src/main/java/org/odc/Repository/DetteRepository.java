package org.odc.Repository;

import org.odc.Entity.Dette;
import java.util.Date;
import java.util.List;
import java.util.Optional;



public interface DetteRepository extends Repository<Dette> {
    List<Dette> findByDate(Date date);
    //Optional<Dette> findByIdWithClient(Long id);
}
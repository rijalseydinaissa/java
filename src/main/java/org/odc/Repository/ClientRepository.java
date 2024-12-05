package org.odc.Repository;

import org.odc.Entity.Client;
import java.util.List;

public interface ClientRepository extends Repository<Client> {
    List<Client> findByName(String name);
}
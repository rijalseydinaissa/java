package org.odc.Service;

import org.odc.Entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client createClient(Client client);
    List<Client> getClientList();
    Optional<Client>getClientById(long id);
}

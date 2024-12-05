package org.odc.Service;

import jakarta.persistence.EntityManager;
import org.odc.Repository.ClientRepository;
import org.odc.Entity.Client;
import java.util.List;
import java.util.Optional;
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    // Constructeur par défaut
    public ClientServiceImpl() {
    }

    // Constructeur avec ClientRepository
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getClientList() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }

    public boolean clientExists(long id) {
        return clientRepository.findById(id).isPresent();
    }

    public List<Client> findClientsByName(String name) {
        return clientRepository.findByName(name);
    }
}
package org.odc.Service;

import org.odc.Repository.CompteRepository;
import org.odc.Entity.Compte;
import java.util.List;
import java.util.Optional;

public class CompteServiceImpl implements CompteService {
    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Compte createCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public List<Compte> getCompteList() {
        return compteRepository.findAll();
    }

    @Override
    public Optional<Compte> getCompteById(long id) {
        return compteRepository.findById(id);
    }

    @Override
    public List<Compte> getComptesByClientId(long clientId) {
        return compteRepository.findByClientId(clientId);
    }

    public boolean compteExists(long id) {
        return compteRepository.findById(id).isPresent();
    }
}
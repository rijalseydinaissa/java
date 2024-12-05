package org.odc.Service;

import org.odc.Entity.Paiement;
import org.odc.Entity.Dette;
import org.odc.Repository.PaiementRepository;
import org.odc.Repository.DetteRepository;

import java.util.List;
import java.util.Optional;

public class PaiementServiceImpl implements PaiementService {
    private final PaiementRepository paiementRepository;
    private final DetteRepository detteRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository, DetteRepository detteRepository) {
        this.paiementRepository = paiementRepository;
        this.detteRepository = detteRepository;
    }

    @Override
    public Paiement createPaiement(Paiement paiement) {
        Optional<Dette> optionalDette = detteRepository.findById(paiement.getDette().getId());
        if (optionalDette.isEmpty()) {
            throw new IllegalArgumentException("Dette non trouvée");
        }
        Dette dette = optionalDette.get();

        validatePaiement(paiement, dette);

        double nouveauMontantRestant = dette.getMontantRestant() - paiement.getMontant();
        dette.setMontantRestant(nouveauMontantRestant);

        detteRepository.save(dette);

        return paiementRepository.save(paiement);
    }

    private void validatePaiement(Paiement paiement, Dette dette) {
        if (paiement.getMontant() == null || paiement.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant du paiement doit être positif");
        }

        if (paiement.getMontant() > dette.getMontantRestant()) {
            throw new IllegalArgumentException(
                    String.format("Le montant du paiement (%f) ne peut pas dépasser le montant restant de la dette (%f)",
                            paiement.getMontant(), dette.getMontantRestant())
            );
        }
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Optional<Optional<Paiement>> getPaiementById(Long id) {
        return Optional.ofNullable(paiementRepository.findById(id));
    }

    @Override
    public void updatePaiement(Paiement paiement) {
        Optional<Paiement> existingPaiement = paiementRepository.findById(paiement.getId());
        if (existingPaiement.isEmpty()) {
            throw new IllegalArgumentException("Paiement non trouvé");
        }

        Dette dette = existingPaiement.get().getDette();

        // Annuler l'ancien paiement
        dette.setMontantRestant(dette.getMontantRestant() + existingPaiement.get().getMontant());

        // Valider et appliquer le nouveau paiement
        validatePaiement(paiement, dette);
        dette.setMontantRestant(dette.getMontantRestant() - paiement.getMontant());

        detteRepository.save(dette);
        paiementRepository.save(paiement);
    }

    @Override
    public void deletePaiement(Long id) {
        Optional<Paiement> optionalPaiement = paiementRepository.findById(id);
        if (optionalPaiement.isPresent()) {
            Paiement paiement = optionalPaiement.get();
            Dette dette = paiement.getDette();
            dette.setMontantRestant(dette.getMontantRestant() + paiement.getMontant());
            detteRepository.save(dette);
            paiementRepository.delete(id);
        }
    }

    @Override
    public List<Paiement> getPaiementsByDetteId(Long detteId) {
        return paiementRepository.findByDetteId(detteId);
    }
}
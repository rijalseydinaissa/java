package org.odc.Views;

import org.odc.Entity.Paiement;
import org.odc.Entity.Dette;
import org.odc.Service.PaiementService;
import org.odc.Service.DetteService;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class PaiementView {
    private final PaiementService paiementService;
    private final DetteService detteService;
    private final Scanner scanner;

    public PaiementView(PaiementService paiementService, DetteService detteService) {
        this.paiementService = paiementService;
        this.detteService = detteService;
        this.scanner = new Scanner(System.in);
    }


    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menu Paiement ---");
            System.out.println("1. Créer un nouveau paiement");
            System.out.println("2. Afficher tous les paiements");
            System.out.println("3. Rechercher un paiement par ID");
            System.out.println("4. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    createPaiement();
                    break;
                case 2:
                    displayAllPaiements();
                    break;
                case 3:
                    searchPaiementById();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createPaiement() {
        try {
            System.out.print("Entrez l'ID de la dette pour ce paiement : ");
            long detteId = scanner.nextLong();
            scanner.nextLine(); // Consommer la nouvelle ligne

            Optional<Dette> optionalDette = detteService.getDetteById(detteId);
            if (optionalDette.isEmpty()) {
                System.out.println("Dette non trouvée. Création de paiement annulée.");
                return;
            }

            Dette dette = optionalDette.get();
            System.out.println("Montant total de la dette : " + dette.getMontantTotal());
            System.out.println("Montant restant à payer : " + dette.getMontantRestant());

            double montant;
            while (true) {
                System.out.print("Entrez le montant du paiement : ");
                montant = scanner.nextDouble();
                scanner.nextLine(); // Consommer la nouvelle ligne

                if (montant <= 0) {
                    System.out.println("Le montant du paiement doit être positif. Veuillez réessayer.");
                } else if (montant > dette.getMontantRestant()) {
                    System.out.println("Le montant du paiement ne peut pas dépasser le montant restant de la dette. Veuillez réessayer.");
                } else {
                    break;
                }
            }

            // Créer le nouveau paiement directement avec la dette (sans Optional)
            Paiement newPaiement = new Paiement(montant, dette);

            Paiement createdPaiement = paiementService.createPaiement(newPaiement);
            System.out.println("Paiement créé avec succès : " + createdPaiement);
            System.out.println("Nouveau montant restant à payer : " + dette.getMontantRestant());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de la création du paiement : " + e.getMessage());
        }
    }

    private void displayAllPaiements() {
        List<Paiement> paiements = paiementService.getAllPaiements();
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
        } else {
            System.out.println("Liste de tous les paiements :");
            for (Paiement paiement : paiements) {
                System.out.println(paiement);
            }
        }
    }

    private void searchPaiementById() {
        System.out.print("Entrez l'ID du paiement : ");
        long paiementId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Optional<Paiement>> paiement = paiementService.getPaiementById(paiementId);
        if (paiement.isPresent()) {
            System.out.println("Paiement trouvé : " + paiement.get());
        } else {
            System.out.println("Aucun paiement trouvé avec l'ID : " + paiementId);
        }
    }
}

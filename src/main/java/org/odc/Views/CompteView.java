package org.odc.Views;

import org.odc.Entity.Compte;
import org.odc.Entity.Client;
import org.odc.Service.CompteService;
import org.odc.Service.ClientService;

import java.util.List;
import java.util.Scanner;

public class CompteView {
    private final CompteService compteService;
    private final ClientService clientService;
    private final Scanner scanner;

    public CompteView(CompteService compteService, ClientService clientService) {
        this.compteService = compteService;
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menu Compte ---");
            System.out.println("1. Créer un nouveau compte");
            System.out.println("2. Afficher tous les comptes");
            System.out.println("3. Rechercher un compte par ID");
            System.out.println("4. Afficher les comptes d'un client");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    createCompte();
                    break;
                case 2:
                    displayAllComptes();
                    break;
                case 3:
                    searchCompteById();
                    break;
                case 4:
                    displayComptesByClientId();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createCompte() {
        System.out.print("Entrez le numéro du compte : ");
        String numero = scanner.nextLine();
        System.out.print("Entrez l'ID du client : ");
        long clientId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        clientService.getClientById(clientId).ifPresentOrElse(
                client -> {
                    Compte newCompte = new Compte(numero, clientId);
                    Compte createdCompte = compteService.createCompte(newCompte);
                    System.out.println("Compte créé avec succès : " + createdCompte);
                },
                () -> System.out.println("Aucun client trouvé avec l'ID : " + clientId)
        );
    }

    private void displayAllComptes() {
        List<Compte> comptes = compteService.getCompteList();
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé.");
        } else {
            System.out.println("Liste des comptes :");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private void searchCompteById() {
        System.out.print("Entrez l'ID du compte : ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        compteService.getCompteById(id).ifPresentOrElse(
                compte -> System.out.println("Compte trouvé : " + compte),
                () -> System.out.println("Aucun compte trouvé avec l'ID : " + id)
        );
    }

    private void displayComptesByClientId() {
        System.out.print("Entrez l'ID du client : ");
        long clientId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        List<Compte> comptes = compteService.getComptesByClientId(clientId);
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé pour ce client.");
        } else {
            System.out.println("Comptes du client :");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }
}
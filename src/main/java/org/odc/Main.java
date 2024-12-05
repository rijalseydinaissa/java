package org.odc;

import org.odc.Factory.ServiceFactory;
import org.odc.Repository.ClientRepository;
import org.odc.Repository.ClientRepositoryDb;
import org.odc.Service.*;
import org.odc.Views.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Get the ServiceFactory instance
        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        // Initialize services using the factory
        ClientService clientService = (ClientService) serviceFactory.getClientService();
        ArticleService articleService = (ArticleService) serviceFactory.getArticleService();
        DetteService detteService = (DetteService) serviceFactory.getDetteService();
        PaiementService paiementService = (PaiementService) serviceFactory.getPaiementService();
        CompteService compteService = (CompteService) serviceFactory.getCompteService();

        // Initialize views
        ClientView clientView = new ClientView(clientService);
        ArticleView articleView = new ArticleView(articleService);
        DetteView detteView = new DetteView(detteService, clientService, articleService);
        PaiementView paiementView = new PaiementView(paiementService, detteService);
        CompteView compteView = new CompteView(compteService,clientService);


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Gestion des clients");
            System.out.println("2. Gestion des articles");
            System.out.println("3. Gestion des dettes");
            System.out.println("4. Gestion des paiements");
            System.out.println("5. Gestion des comptes");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option : ");
            try {
                if (!scanner.hasNextInt()) { // Vérifie si l'entrée est un entier
                    System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                    scanner.next(); // Consomme l'entrée incorrecte
                    continue; // Redémarre la boucle
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                switch (choice) {
                    case 1:
                        clientView.showMenu(); // Afficher le menu client
                        break;
                    case 2:
                        articleView.showMenu(); // Afficher le me3nu article
                        break;
                    case 3:
                        detteView.showMenu(); // Afficher le menu des dettes
                        break;
                    case 4:
                        paiementView.showMenu(); // Afficher le menu des paiements
                        break;
                    case 5:
                        compteView.showMenu(); // Afficher le menu des paiements
                        break;
                    case 6:
                        System.out.println("Au revoir !");
                        scanner.close(); // Fermer le scanner
                        return; // Quitte le programme
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Entrée incorrecte. Veuillez entrer un nombre.");
                scanner.nextLine(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
    }
}

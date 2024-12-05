package org.odc.Views;


import org.odc.Entity.Client;
import org.odc.Service.ClientService;

import java.util.List;
import java.util.Scanner;

public class ClientView {
    private final ClientService clientService;
    private final Scanner scanner;

    public ClientView(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menu Client ---");
            System.out.println("1. Créer un nouveau client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Rechercher un client par ID");
            System.out.println("4. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    displayAllClients();
                    break;
                case 3:
                    searchClientById();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createClient() {
        System.out.print("Entrez le nom du client : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le prénom du client : ");
        String prenom = scanner.nextLine();

        Client newClient = new Client(nom, prenom);
        Client createdClient = clientService.createClient(newClient);
        System.out.println("Client créé avec succès : " + createdClient);
    }

    private void displayAllClients() {
        List<Client> clients = clientService.getClientList();
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.println("Liste des clients :");
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

    private void searchClientById() {
        System.out.print("Entrez l'ID du client : ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        clientService.getClientById(id).ifPresentOrElse(
                client -> System.out.println("Client trouvé : " + client),
                () -> System.out.println("Aucun client trouvé avec l'ID : " + id)
        );
    }
}

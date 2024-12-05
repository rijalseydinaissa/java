package org.odc.Views;

import org.odc.Entity.Dette;
import org.odc.Entity.Client;
import org.odc.Entity.Article;
import org.odc.Service.DetteService;
import org.odc.Service.ClientService;
import org.odc.Service.ArticleService;
import org.odc.Entity.ArticleDette;

import java.util.*;

public class DetteView {
    private final DetteService detteService;
    private final ClientService clientService;
    private final ArticleService articleService;
    private final Scanner scanner;

    public DetteView(DetteService detteService, ClientService clientService, ArticleService articleService) {
        this.detteService = detteService;
        this.clientService = clientService;
        this.articleService = articleService;
        this.scanner = new Scanner(System.in);
    }

    // Méthode showMenu() existante...

    public void createDette() {
        try {
            // Création de la nouvelle dette
            Dette dette = new Dette();
            dette.setDateCreation(new Date());
            List<ArticleDette> articlesDette = new ArrayList<>();
            double montantTotal = 0.0;

            // Demande de l'ID du client
            System.out.print("Entrez l'ID du client pour cette dette : ");
            long clientId = scanner.nextLong();
            Optional<Client> clientOpt = clientService.getClientById(clientId);

            if (clientOpt.isEmpty()) {
                System.out.println("Client non trouvé.");
                return;
            }
            dette.setClient(clientOpt.get());

            // Boucle pour ajouter des articles
            while (true) {
                System.out.print("Entrez l'ID de l'article (ou 0 pour terminer) : ");
                long articleId = scanner.nextLong();

                if (articleId == 0) {
                    break;
                }

                Optional<Article> articleOpt = articleService.getArticleById(articleId);
                if (articleOpt.isEmpty()) {
                    System.out.println("Article non trouvé.");
                    continue;
                }

                Article article = articleOpt.get();
                System.out.println("Article trouvé : " + article);

                System.out.print("Entrez la quantité : ");
                int quantite = scanner.nextInt();

                if (quantite > article.getQte()) {
                    System.out.println("Stock insuffisant. Stock disponible : " + article.getQte());
                    continue;
                }

                // Calculer le montant pour cet article
                double totalArticle = article.getPrix() * quantite;
                montantTotal += totalArticle;

                // Création de l'ArticleDette
                ArticleDette articleDette = new ArticleDette();
                articleDette.setArticle(article);
                articleDette.setQuantite(quantite);
                articleDette.setDette(dette);
                articlesDette.add(articleDette);
            }

            if (articlesDette.isEmpty()) {
                System.out.println("Aucun article ajouté à la dette.");
                return;
            }

            System.out.println(montantTotal);
            // Configuration de la dette
            dette.setArticles(articlesDette);
            dette.setMontantTotal(montantTotal);
            dette.setMontantRestant(montantTotal); // Assurez-vous de définir également le montant restant

            // Sauvegarde de la dette
            Dette nouvelleDette = detteService.createDette(dette);
            System.out.println("Dette créée avec succès. ID : " + nouvelleDette.getId());

        } catch (Exception e) {
            System.out.println("Erreur lors de la création de la dette : " + e.getMessage());
            e.printStackTrace(); // Ajout pour un meilleur débogage
        }
    }




    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menu Dette ---");
            System.out.println("1. Créer une nouvelle dette");
            System.out.println("2. Afficher tous les dettes");
            System.out.println("3. Rechercher une dette par ID");
            System.out.println("4. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    createDette();
                    break;
                case 2:
                    getAllDebts();
                    break;
                case 3:
                    searchDetteById();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

 private void getAllDebts(){
        List<Dette> debts = detteService.getAllDettes();
        System.out.println("Liste des dettes :");
        for (Dette debt : debts) {
            System.out.println(debt);
        }

 }

    private void searchDetteById() {
        System.out.print("Entrez l'ID de la dette : ");
        long detteId = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne
        Optional<Dette> detteOpt = detteService.getDetteById(detteId);
        if (detteOpt.isPresent()) {
            System.out.println("Dette trouvée : " + detteOpt.get());
        } else {
            System.out.println("Aucune dette trouvée avec l'ID : " + detteId);
        }
    }
}

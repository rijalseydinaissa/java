package org.odc.Views;

import org.odc.Entity.*;
import org.odc.Service.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ArticleView {
    private final ArticleService articleService;
    private final Scanner scanner;

    public ArticleView(ArticleService articleService) {
        this.articleService = articleService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menu Article ---");
            System.out.println("1. Créer un nouvel article");
            System.out.println("2. Afficher tous les articles");
            System.out.println("3. Rechercher un article par ID");
            System.out.println("4. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    createArticle();
                    break;
                case 2:
                    displayAllArticles();
                    break;
                case 3:
                    searchArticleById();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createArticle() {
        try {
            System.out.print("Entrez le nom de l'article : ");
            String nom = scanner.nextLine();
            System.out.print("Entrez le prix de l'article : ");
            double prix = scanner.nextDouble();
            System.out.print("Entrez la quantité en stock : ");
            int qte = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            Article newArticle = new Article(nom, prix, qte);
            Article createdArticle = articleService.createArticle(newArticle);
            System.out.println("Article créé avec succès : " + createdArticle);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création de l'article : " + e.getMessage());
        }
    }

    private void displayAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        if (articles.isEmpty()) {
            System.out.println("Aucun article trouvé.");
        } else {
            System.out.println("\nListe des articles :");
            for (Article article : articles) {
                System.out.println(article);
            }
        }
    }

    private void searchArticleById() {
        System.out.print("Entrez l'ID de l'article à rechercher : ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Optional<Article> articleOpt = articleService.getArticleById(id);
        if (articleOpt.isPresent()) {
            System.out.println("Article trouvé : " + articleOpt.get());
        } else {
            System.out.println("Aucun article trouvé avec l'ID fourni.");
        }
    }
}

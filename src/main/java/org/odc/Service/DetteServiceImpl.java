package org.odc.Service;

import org.odc.Entity.Dette;
import org.odc.Entity.Article;
import org.odc.Entity.ArticleDette;
import org.odc.Repository.DetteRepository;
import org.odc.Repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

public class DetteServiceImpl implements DetteService {
    private final DetteRepository detteRepository;
    private final ArticleRepository articleRepository;

    public DetteServiceImpl(DetteRepository detteRepository, ArticleRepository articleRepository) {
        this.detteRepository = detteRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Dette createDette(Dette dette) {
        // Vérification des stocks
        for (ArticleDette articleDette : dette.getArticles()) {
            Optional<Article> optionalArticle = articleRepository.findById(articleDette.getArticle().getId());
            if (optionalArticle.isEmpty()) {
                throw new RuntimeException("Article non trouvé");
            }
            Article article = optionalArticle.get();
            if (article.getQte() < articleDette.getQuantite()) {
                throw new RuntimeException("Stock insuffisant pour l'article " + article.getNom());
            }
        }

        // Mise à jour des stocks
        for (ArticleDette articleDette : dette.getArticles()) {
            Article article = articleDette.getArticle();
            article.setQte(article.getQte() - articleDette.getQuantite());
            articleRepository.save(article);
        }
        System.out.println("Montant total calculé : " + "200000000");
        // Calcul automatique du montant total si nécessaire
        double total = dette.getArticles().stream()
                .mapToDouble(ad -> ad.getArticle().getPrix() * ad.getQuantite())
                .sum();
        dette.setMontantTotal(total);
        dette.setMontantRestant(total);

        System.out.println("Montant total calculé : " + total);
        System.out.println("Dette avant sauvegarde : " + dette);

        return detteRepository.save(dette);
    }

    @Override
    public List<Dette> getAllDettes() {
        return detteRepository.findAll();
    }

    @Override
    public Optional<Dette> getDetteById(Long id) {
        return detteRepository.findById(id);
    }

    @Override
    public void updateDette(Dette dette) {
        detteRepository.save(dette);
    }

    @Override
    public void deleteDette(Long id) {
        detteRepository.delete(id);
    }
}
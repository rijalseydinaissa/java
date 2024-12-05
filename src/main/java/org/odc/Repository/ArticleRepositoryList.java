package org.odc.Repository;

import org.odc.Entity.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


public class ArticleRepositoryList extends RepositoryList<Article> implements ArticleRepository {
    private List<Article> articles;

    public ArticleRepositoryList() {
        this.articles = new ArrayList<>();
    }
    @Override
    public List<Article> findByName(String nom) {
        return entities.stream()
                .filter(c -> c.getNom().equalsIgnoreCase(nom))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStock(Long id, int quantity) {
        findById(id).ifPresent(article -> {
            article.setQte(article.getQte() - quantity);
        });
    }

}
package org.odc.Service;

import org.odc.Entity.*;
import org.odc.Repository.*;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article createArticle(Article article);
    List<Article> getAllArticles();
    Optional<Article> getArticleById(Long id);
    void updateStock(Long id, int quantite);
}
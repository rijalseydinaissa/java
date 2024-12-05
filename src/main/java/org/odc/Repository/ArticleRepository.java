package org.odc.Repository;

import org.odc.Entity.Article;
import java.util.List;

public interface ArticleRepository extends Repository<Article> {
    List<Article> findByName(String nom);
    void updateStock(Long articleId, int quantity);
}

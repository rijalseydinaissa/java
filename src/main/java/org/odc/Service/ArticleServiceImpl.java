package org.odc.Service;

import org.odc.Entity.Article;
import org.odc.Repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public void updateStock(Long id, int quantite) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setQte(quantite);
            articleRepository.save(article);
        } else {
            throw new RuntimeException("Article not found with id: " + id);
        }
    }

    public boolean articleExists(Long id) {
        return articleRepository.findById(id).isPresent();
    }

    public List<Article> findArticlesByName(String name) {
        return articleRepository.findByName(name);
    }
}
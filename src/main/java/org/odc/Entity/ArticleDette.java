package org.odc.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articles_dette")
public class ArticleDette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dette_id", nullable = false)
    private Dette dette;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(nullable = false)
    private Integer quantite;

    // Constructeur avec paramètres
    public ArticleDette(Article article, Dette dette, int quantite) {
        this.article = article;
        this.dette = dette;
        this.quantite = quantite;
    }

    public ArticleDette() {

    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Dette getDette() {
        return dette;
    }

    public void setDette(Dette dette) {
        this.dette = dette;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

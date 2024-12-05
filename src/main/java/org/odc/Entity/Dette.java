package org.odc.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dettes")
public class Dette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;

    @Column(name = "montantTotal", nullable = false)
    private Double montantTotal;

    @Column(name = "montantRestant", nullable = false)
    private Double montantRestant;

    @OneToMany(mappedBy = "dette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleDette> articles = new ArrayList<>();


    // Constructeur par défaut
    public Dette() {
        this.articles = new ArrayList<>();
        this.montantTotal = 0.0;
        this.montantRestant = 0.0;
    }

    // Constructeur avec client
    public Dette(Client client) {
        this();
        this.client = client;
    }

    // Constructeur complet
    public Dette(Client client, Double montantTotal) {
        this(client);
        this.montantTotal = montantTotal;
        this.montantRestant = montantTotal;
    }

    @PrePersist
    protected void onCreate() {
        if (montantRestant == null) {
            montantRestant = montantTotal;
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date date) {
        this.dateCreation = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(Double montantRestant) {
        this.montantRestant = montantRestant;
    }



    public List<ArticleDette> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDette> articles) {
        this.articles = articles;
    }

    // Méthodes utilitaires
    public void addArticleDette(ArticleDette articleDette) {
        articles.add(articleDette);
        articleDette.setDette(this);
    }

    public void removeArticleDette(ArticleDette articleDette) {
        articles.remove(articleDette);
        articleDette.setDette(null);
    }

    private void updateMontantTotal() {
        this.montantTotal = articles.stream()
                .mapToDouble(ad -> ad.getArticle().getPrix() * ad.getQuantite())
                .sum();
        if (this.montantRestant == null || this.montantRestant > this.montantTotal) {
            this.montantRestant = this.montantTotal;
        }
    }

    @Override
    public String toString() {
        return "Dette{" +
                "id=" + id +
                ", client=" + (client != null ? client.getNom() + " " + client.getPrenom() : "null") +
                ", montantTotal=" + montantTotal +
                ", montantRestant=" + montantRestant +
                '}';
    }
}
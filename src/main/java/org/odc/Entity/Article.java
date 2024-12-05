package org.odc.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix")
    private double prix;

    @Column(name = "qte")
    private int qte;

    // Constructeur par défaut (nécessaire pour la réflexion)
    public Article() {
    }
    public Article(String nom, double prix, int qte) {
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", qte=" + qte +
                '}';
    }
}


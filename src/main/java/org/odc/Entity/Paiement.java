package org.odc.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "paiements")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dette_id", nullable = false)
    private Dette dette;

    @Column(name = "date_paiement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePaiement;

    @PrePersist
    protected void onCreate() {
        if (datePaiement == null) {
            datePaiement = new Date();
        }
    }

    // Constructeurs
    public Paiement() {
    }

    public Paiement(Double montant, Dette dette) {
        this.montant = montant;
        this.dette = dette;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Dette getDette() {
        return dette;
    }

    public void setDette(Dette dette) {
        this.dette = dette;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }
    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", montant=" + montant +
                ", dette=" + (dette != null ? "Dette #" + dette.getId() : "null") +
                ", datePaiement=" + datePaiement +
                '}';
    }
}
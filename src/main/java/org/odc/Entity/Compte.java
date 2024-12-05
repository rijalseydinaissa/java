package org.odc.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comptes")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "client_id")
    private Long clientId;

    public Compte() {}  // Constructeur par défaut nécessaire pour JPA

    public Compte(String numero, Long clientId) {
        this.numero = numero;
        this.clientId = clientId;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
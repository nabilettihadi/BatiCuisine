package main.java.com.baticuisine.model;

import java.util.UUID;
import java.time.LocalDate;

public class Devis {
    private UUID id;
    private double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;
    private Projet projet;
    private Client client;

    // Constructeurs
    public Devis() {
        this.id = UUID.randomUUID();
    }

    public Devis(UUID id, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, Projet projet, Client client) {
        this.id = id;
        this.montantEstime = montantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.accepte = accepte;
        this.projet = projet;
        this.client = client;
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public boolean isAccepte() {
        return accepte;
    }

    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

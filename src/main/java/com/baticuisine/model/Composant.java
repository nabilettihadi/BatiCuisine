package main.java.com.baticuisine.model;

import java.util.UUID;
import main.java.com.baticuisine.enums.TypeComposant;

public abstract class Composant {
    private final UUID id;
    private String nom;
    private double coutUnitaire;
    private double quantite;
    private TypeComposant typeComposant;
    private double tauxTVA;

    // Constructeur
    public Composant(UUID id, String nom, double coutUnitaire, double quantite, TypeComposant typeComposant, double tauxTVA) {
        this.id = id;
        this.nom = nom;
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    // Méthode abstraite pour calculer le coût total
    public abstract double calculerCoutTotal();
}

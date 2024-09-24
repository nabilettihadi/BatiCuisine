package main.java.com.baticuisine.model;

import main.java.com.baticuisine.enums.TypeComposant;

import java.util.UUID;

public class MainDoeuvre extends Composant {
    private double heuresTravail;
    private double productiviteOuvrier;

    // Constructeur
    public MainDoeuvre(UUID id,String nom, double coutUnitaire, double quantite, TypeComposant typeComposant, double tauxTVA, double heuresTravail, double productiviteOuvrier) {
        super(id, nom, coutUnitaire, quantite, typeComposant, tauxTVA);
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    // Getters et Setters
    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    // Calcul du coût total pour la main-d'œuvre
    @Override
    public double calculerCoutTotal() {
        return getCoutUnitaire() * heuresTravail * productiviteOuvrier * (1 + getTauxTVA());
    }

    public String toString() {
        return "Main D'oeuvre [ID: " + getId() + ", Nom: " + getNom() +
                ", Coût Unitaire: " + getCoutUnitaire() + ", Quantité: " + getQuantite() +
                ", TVA: " + getTauxTVA() + "]";
    }
}

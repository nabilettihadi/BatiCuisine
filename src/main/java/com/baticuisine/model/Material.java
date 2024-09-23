package main.java.com.baticuisine.model;

import main.java.com.baticuisine.enums.TypeComposant;

import java.util.UUID;

public class Material extends Composant {
    private double coutTransport;
    private double coefficientQualite;

    // Constructeur
    public Material(UUID id, String nom, double coutUnitaire, double quantite,TypeComposant typeComposant, double tauxTVA, double coutTransport, double coefficientQualite) {
        super(id, nom, coutUnitaire, quantite,typeComposant, tauxTVA);
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }

    // Getters et Setters
    public double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }

    // Calcul du coût total pour le matériau
    @Override
    public double calculerCoutTotal() {
        return (getCoutUnitaire() * getQuantite() + coutTransport) * coefficientQualite * (1 + getTauxTVA());
    }

    public String toString() {
        return "Main d'oeuvre [ID: " + getId() + ", Nom: " + getNom() +
                ", Coût Unitaire: " + getCoutUnitaire() + ", Quantité: " + getQuantite() +
                ", TVA: " + getTauxTVA() + "]";
    }
}

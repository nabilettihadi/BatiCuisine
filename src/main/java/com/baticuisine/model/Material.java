package main.java.com.baticuisine.model;

import main.java.com.baticuisine.enums.TypeComposant;

public class Material extends Composant {
    private double coutTransport;
    private double coefficientQualite;

    // Constructeur
    public Material(String nom, double coutUnitaire, double quantite, double tauxTVA, double coutTransport, double coefficientQualite) {
        super(nom, coutUnitaire, quantite, TypeComposant.MATERIAU, tauxTVA);
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
}

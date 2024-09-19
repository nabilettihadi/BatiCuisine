package main.java.com.baticuisine.model;

import main.java.com.baticuisine.enums.TypeComposant;

public class MainDoeuvre extends Composant {
    private double heuresTravail;
    private double productiviteOuvrier;

    // Constructeur
    public MainDoeuvre(String nom, double coutUnitaire, double quantite, double tauxTVA, double heuresTravail, double productiviteOuvrier) {
        super(nom, coutUnitaire, quantite, TypeComposant.MAIN_DOEUVRE, tauxTVA);
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
}

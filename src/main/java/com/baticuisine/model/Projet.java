package main.java.com.baticuisine.model;

import main.java.com.baticuisine.enums.EtatProjet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Projet {
    private UUID id;
    private String nomProjet;
    private Client client;
    private double tauxTVA;
    private double margeBeneficiaire;
    private List<Material> materiaux;
    private List<MainDoeuvre> mainDoeuvre;
    private EtatProjet etatProjet;
    private double coutTotal;

    public Projet() {
        this.id = UUID.randomUUID();
        this.materiaux = new ArrayList<>();
        this.mainDoeuvre = new ArrayList<>();
    }

    // Getters et Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public List<Material> getMateriaux() {
        return materiaux;
    }

    public void ajouterMateriau(Material materiau) {
        this.materiaux.add(materiau);
    }

    public List<MainDoeuvre> getMainDoeuvre() {
        return mainDoeuvre;
    }

    public void ajouterMainDoeuvre(MainDoeuvre mainDoeuvre) {
        this.mainDoeuvre.add(mainDoeuvre);
    }

    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }

    public double getCoutTotal() {
        return coutTotal; // Récupère le coût total
    }

    public void setCoutTotal(double coutTotal) { // Setter pour le coût total
        this.coutTotal = coutTotal;
    }

    // Méthode pour calculer le coût total du projet
    public double calculerCoutTotal() {
        double coutMateriaux = materiaux.stream()
                .mapToDouble(Material::calculerCoutTotal)
                .sum();

        double coutMainDoeuvre = mainDoeuvre.stream()
                .mapToDouble(MainDoeuvre::calculerCoutTotal)
                .sum();

        double total = coutMateriaux + coutMainDoeuvre;

        if (tauxTVA > 0) {
            total += total * (tauxTVA / 100);
        }
        if (margeBeneficiaire > 0) {
            total += total * (margeBeneficiaire / 100);
        }

        setCoutTotal(total); // Met à jour le coût total
        return total;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Nom: %s | Client: %s | Coût Total: %.2f €",
                id, nomProjet, (client != null ? client.getNom() : "Non spécifié"), coutTotal);
    }
}

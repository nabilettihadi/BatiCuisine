package main.java.com.baticuisine.model;

import main.java.com.baticuisine.enums.EtatProjet;

import java.util.UUID;
import java.util.List;

public class Projet {
    private UUID id;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private EtatProjet etatProjet;
    private Client client;
    private List<Material> materiaux;
    private List<MainDoeuvre> mainDoeuvre;
    private Devis devis;

    // Constructeurs
    public Projet() {
        this.id = UUID.randomUUID();
    }

    public Projet(UUID id, String nomProjet, double margeBeneficiaire, double coutTotal, EtatProjet etatProjet, Client client, List<Material> materiaux, List<MainDoeuvre> mainDoeuvre, Devis devis) {
        this.id = id;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
        this.client = client;
        this.materiaux = materiaux;
        this.mainDoeuvre = mainDoeuvre;
        this.devis = devis;
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

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Material> getMateriaux() {
        return materiaux;
    }

    public void setMateriaux(List<Material> materiaux) {
        this.materiaux = materiaux;
    }

    public List<MainDoeuvre> getMainDoeuvre() {
        return mainDoeuvre;
    }

    public void setMainDoeuvre(List<MainDoeuvre> mainDoeuvre) {
        this.mainDoeuvre = mainDoeuvre;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }


}

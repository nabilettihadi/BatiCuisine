package main.java.com.baticuisine.ui;

import main.java.com.baticuisine.enums.TypeComposant;
import main.java.com.baticuisine.model.Composant;
import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.service.ComposantService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ComposantUI {

    private final Scanner scanner;
    private final ComposantService<MainDoeuvre> mainDoeuvreService;
    private final ComposantService<Material> materialService;
    private final ComposantService<Composant> composantService; // Add composantService

    public ComposantUI(ComposantService<MainDoeuvre> mainDoeuvreService, ComposantService<Material> materialService, ComposantService<Composant> composantService) {
        this.mainDoeuvreService = mainDoeuvreService;
        this.materialService = materialService;
        this.composantService = composantService; // Initialize composantService
        this.scanner = new Scanner(System.in);
    }

    public void startComposantMenu() {
        while (true) {
            System.out.println("=== Menu de Gestion des Composants ===");
            System.out.println("1. Ajouter un composant");
            System.out.println("2. Afficher tous les composants");
            System.out.println("3. Rechercher un composant par ID");
            System.out.println("4. Mettre à jour un composant");
            System.out.println("5. Supprimer un composant");
            System.out.println("0. Retourner au menu principal");
            System.out.print("Choisissez une option: ");

            int option = saisirOptionEntiere();

            switch (option) {
                case 1:
                    addComposant();
                    break;
                case 2:
                    displayAllComposants();
                    break;
                case 3:
                    displayComposantById();
                    break;
                case 4:
                    updateComposant();
                    break;
                case 5:
                    deleteComposant();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void addComposant() {
        try {
            System.out.println("Choisissez le type de composant (1: MAIN_DOEUVRE, 2: MATERIAL): ");
            int typeComposant = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            System.out.print("Entrez le nom du composant: ");
            String nom = scanner.nextLine();
            System.out.print("Entrez le coût unitaire du composant: ");
            double coutUnitaire = saisirDouble();
            System.out.print("Entrez la quantité du composant: ");
            double quantite = saisirDouble();
            System.out.print("Entrez le taux de TVA: ");
            double tauxTVA = saisirDouble();

            UUID id = UUID.randomUUID();
            TypeComposant type;

            if (typeComposant == 1) {
                type = TypeComposant.MAIN_DOEUVRE;
                System.out.print("Entrez le nombre d'heures de travail: ");
                double heuresTravail = saisirDouble();
                System.out.print("Entrez la productivité de l'ouvrier: ");
                double productiviteOuvrier = saisirDouble();
                MainDoeuvre mainDoeuvre = new MainDoeuvre(id, nom, coutUnitaire, quantite, type, tauxTVA, heuresTravail, productiviteOuvrier);
                mainDoeuvreService.save(mainDoeuvre);
            } else if (typeComposant == 2) {
                type = TypeComposant.MATERIAU;
                System.out.print("Entrez le coût de transport: ");
                double coutTransport = saisirDouble();
                System.out.print("Entrez le coefficient de qualité: ");
                double coefficientQualite = saisirDouble();
                Material material = new Material(id, nom, coutUnitaire, quantite, type, tauxTVA, coutTransport, coefficientQualite);
                materialService.save(material);
            } else {
                System.out.println("Type de composant invalide.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erreur: veuillez entrer une valeur numérique valide.");
            scanner.nextLine(); // Clear the buffer
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private double saisirDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Erreur: veuillez entrer une valeur décimale valide.");
                scanner.nextLine(); // Clear the buffer
            }
        }
    }

    private int saisirOptionEntiere() {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                return option;
            } catch (InputMismatchException e) {
                System.out.println("Erreur: veuillez entrer une valeur entière valide.");
                scanner.nextLine(); // Clear the buffer
            }
        }
    }

    private void displayAllComposants() {
        try {
            List<Composant> composants = composantService.findAll();
            if (composants.isEmpty()) {
                System.out.println("Aucun composant trouvé.");
            } else {
                for (Composant composant : composants) {
                    afficherDetailsComposant(composant);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des composants: " + e.getMessage());
        }
    }

    private void displayComposantById() {
        try {
            System.out.print("Entrez l'ID du composant à rechercher (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());
            Composant composant = composantService.findById(id);
            if (composant != null) {
                afficherDetailsComposant(composant);
            } else {
                System.out.println("Composant non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du composant: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void afficherDetailsComposant(Composant composant) {
        System.out.println("ID: " + composant.getId());
        System.out.println("Nom: " + composant.getNom());
        System.out.println("Coût Unitaire: " + composant.getCoutUnitaire());
        System.out.println("Quantité: " + composant.getQuantite());
        System.out.println("Taux de TVA: " + composant.getTauxTVA());
        System.out.println("Coût Total: " + composant.calculerCoutTotal());

        if (composant instanceof MainDoeuvre) {
            MainDoeuvre mainDoeuvre = (MainDoeuvre) composant;
            System.out.println("Heures de Travail: " + mainDoeuvre.getHeuresTravail());
            System.out.println("Productivité Ouvrier: " + mainDoeuvre.getProductiviteOuvrier());
        } else if (composant instanceof Material) {
            Material material = (Material) composant;
            System.out.println("Coût de Transport: " + material.getCoutTransport());
            System.out.println("Coefficient de Qualité: " + material.getCoefficientQualite());
        }
        System.out.println("---------------------------");
    }

    private void updateComposant() {
        try {
            System.out.print("Entrez l'ID du composant à mettre à jour (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());
            Composant composant = composantService.findById(id);
            if (composant != null) {
                System.out.print("Entrez le nouveau nom (actuel: " + composant.getNom() + "): ");
                String nom = scanner.nextLine();
                System.out.print("Entrez le nouveau coût unitaire (actuel: " + composant.getCoutUnitaire() + "): ");
                double coutUnitaire = saisirDouble();
                System.out.print("Entrez la nouvelle quantité (actuelle: " + composant.getQuantite() + "): ");
                double quantite = saisirDouble();
                System.out.print("Entrez le nouveau taux de TVA (actuel: " + composant.getTauxTVA() + "): ");
                double tauxTVA = saisirDouble();

                composant.setNom(nom);
                composant.setCoutUnitaire(coutUnitaire);
                composant.setQuantite(quantite);
                composant.setTauxTVA(tauxTVA);
                composantService.update(composant);
                System.out.println("Composant mis à jour avec succès.");
            } else {
                System.out.println("Composant non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du composant: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void deleteComposant() {
        try {
            System.out.print("Entrez l'ID du composant à supprimer (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());
            boolean deleted = composantService.delete(id);
            if (deleted) {
                System.out.println("Composant supprimé avec succès.");
            } else {
                System.out.println("Composant non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du composant: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}

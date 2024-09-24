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

    public ComposantUI(ComposantService<MainDoeuvre> mainDoeuvreService, ComposantService<Material> materialService) {
        this.mainDoeuvreService = mainDoeuvreService;
        this.materialService = materialService;
        this.scanner = new Scanner(System.in);
    }

    public void startComposantMenu() throws SQLException {
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
                scanner.nextLine();
                return option;
            } catch (InputMismatchException e) {
                System.out.println("Erreur: veuillez entrer une valeur entière valide.");
                scanner.nextLine();
            }
        }
    }

    private void displayAllComposants() throws SQLException {
        System.out.println("=== Affichage de tous les composants (MainDoeuvre et Material) ===");


        List<MainDoeuvre> mainDoeuvreList = mainDoeuvreService.findAll();
        if (mainDoeuvreList.isEmpty()) {
            System.out.println("Aucune main d'œuvre trouvée.");
        } else {
            System.out.println("\n--- Liste des MAIN_DOEUVRE ---");
            for (MainDoeuvre mainDoeuvre : mainDoeuvreList) {
                System.out.println("ID: " + mainDoeuvre.getId());
                System.out.println("Nom: " + mainDoeuvre.getNom());
                System.out.println("Coût Unitaire: " + mainDoeuvre.getCoutUnitaire());
                System.out.println("Quantité: " + mainDoeuvre.getQuantite());
                System.out.println("Taux de TVA: " + mainDoeuvre.getTauxTVA());
                System.out.println("Heures de Travail: " + mainDoeuvre.getHeuresTravail());
                System.out.println("Productivité Ouvrier: " + mainDoeuvre.getProductiviteOuvrier());
                System.out.println("Coût Total: " + mainDoeuvre.calculerCoutTotal());
                System.out.println("-----------------------------------");
            }
        }


        List<Material> materialList = materialService.findAll();
        if (materialList.isEmpty()) {
            System.out.println("Aucun matériau trouvé.");
        } else {
            System.out.println("\n--- Liste des MATERIAL ---");
            for (Material material : materialList) {
                System.out.println("ID: " + material.getId());
                System.out.println("Nom: " + material.getNom());
                System.out.println("Coût Unitaire: " + material.getCoutUnitaire());
                System.out.println("Quantité: " + material.getQuantite());
                System.out.println("Taux de TVA: " + material.getTauxTVA());
                System.out.println("Coût de Transport: " + material.getCoutTransport());
                System.out.println("Coefficient de Qualité: " + material.getCoefficientQualite());
                System.out.println("Coût Total: " + material.calculerCoutTotal());
                System.out.println("-----------------------------------");
            }
        }
    }



    private void displayComposantById() throws SQLException {
        System.out.print("Entrez l'ID du composant: ");
        UUID id = UUID.fromString(scanner.nextLine());


        MainDoeuvre mainDoeuvre = mainDoeuvreService.findById(id);
        if (mainDoeuvre != null) {
            System.out.println("Composant trouvé (MAIN_DOEUVRE) : ");
            System.out.println(mainDoeuvre);
            return;
        }


        Material material = materialService.findById(id);

        if (material != null) {
            System.out.println("Composant trouvé (MATERIAL) : ");
            System.out.println(material);
        } else {
            System.out.println("Aucun composant trouvé avec cet ID.");
        }
    }

    private void updateComposant() {
        try {
            System.out.print("Entrez l'ID du composant à mettre à jour (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());


            MainDoeuvre mainDoeuvre = mainDoeuvreService.findById(id);
            if (mainDoeuvre != null) {
                updateComposantDetails(mainDoeuvre);
                mainDoeuvreService.update(mainDoeuvre);
                System.out.println("Main d'œuvre mise à jour avec succès.");
                return;
            }


            Material material = materialService.findById(id);
            if (material != null) {
                updateComposantDetails(material);
                materialService.update(material);
                System.out.println("Matériau mis à jour avec succès.");
            } else {
                System.out.println("Composant non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du composant: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }


    private void updateComposantDetails(Composant composant) {
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
    }

    private void deleteComposant() {
        try {
            System.out.print("Entrez l'ID du composant à supprimer (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());


            boolean deletedMainDoeuvre = mainDoeuvreService.delete(id);
            if (deletedMainDoeuvre) {
                System.out.println("Main d'œuvre supprimée avec succès.");
                return;
            }


            boolean deletedMaterial = materialService.delete(id);
            if (deletedMaterial) {
                System.out.println("Matériau supprimé avec succès.");
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

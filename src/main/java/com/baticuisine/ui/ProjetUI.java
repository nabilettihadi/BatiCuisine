package main.java.com.baticuisine.ui;

import main.java.com.baticuisine.enums.EtatProjet;
import main.java.com.baticuisine.enums.TypeComposant;
import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.model.Projet;
import main.java.com.baticuisine.service.ClientService;
import main.java.com.baticuisine.service.ProjetService;
import main.java.com.baticuisine.service.ComposantService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProjetUI {
    private final ProjetService projetService;
    private final ClientService clientService;
    private final ComposantService<Material> materiauService;
    private final ComposantService<MainDoeuvre> mainDoeuvreService;
    private final Scanner scanner;

    public ProjetUI(ProjetService projetService, ClientService clientService,
                    ComposantService<Material> materiauService,
                    ComposantService<MainDoeuvre> mainDoeuvreService) {
        this.projetService = projetService;
        this.clientService = clientService;
        this.materiauService = materiauService;
        this.mainDoeuvreService = mainDoeuvreService;
        this.scanner = new Scanner(System.in);
    }


    public void startProjetMenu() throws SQLException {
        System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        boolean continuer = true;
        while (continuer) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerNouveauProjet();
                    break;
                case 2:
                    afficherProjets();
                    break;
                case 3:
                    calculerCoutProjet();
                    break;
                case 4:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void creerNouveauProjet() throws SQLException {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int choixClient = scanner.nextInt();
        scanner.nextLine();

        Client client = null;
        if (choixClient == 1) {
            System.out.print("--- Recherche de client existant ---\nEntrez le nom du client : ");
            String nomClient = scanner.nextLine();
            client = clientService.findByName(nomClient);
            if (client != null) {
                System.out.println("Client trouvé !");
                afficherDetailsClient(client);
                System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
                String confirmation = scanner.nextLine();
                if (!confirmation.equalsIgnoreCase("y")) {
                    client = null;
                }
            } else {
                System.out.println("Client non trouvé !");
            }
        } else if (choixClient == 2) {
            client = ajouterNouveauClient();
        }

        if (client != null) {
            System.out.println("--- Création d'un Nouveau Projet ---");
            Projet projet = new Projet();
            projet.setId(UUID.randomUUID());
            projet.setClient(client);

            System.out.print("Entrez le nom du projet : ");
            projet.setNomProjet(scanner.nextLine());


            System.out.print("Entrez le pourcentage de marge bénéficiaire : ");
            projet.setMargeBeneficiaire(scanner.nextDouble());
            scanner.nextLine();

            ajouterMateriaux(projet);
            ajouterMainDoeuvre(projet);
            calculerEtEnregistrerCout(projet);

            projet.setEtatProjet(EtatProjet.EN_COURS);

            try {
                projetService.createProject(projet);
                System.out.println("Projet enregistré avec succès !");
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'enregistrement du projet : " + e.getMessage());
            }
        }
    }

    private void ajouterMateriaux(Projet projet) throws SQLException {
        System.out.println("--- Ajout des matériaux ---");
        boolean continuer = true;
        while (continuer) {
            System.out.print("Entrez le nom du matériau : ");
            String nom = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau : ");
            double quantite = scanner.nextDouble();
            System.out.print("Entrez le coût unitaire de ce matériau : ");
            double coutUnitaire = scanner.nextDouble();
            System.out.print("Entrez le coût de transport de ce matériau : ");
            double coutTransport = scanner.nextDouble();
            System.out.print("Entrez le coefficient de qualité du matériau : ");
            double coefficientQualite = scanner.nextDouble();
            System.out.print("Entrez le taux de TVA : ");
            double tauxTVA = scanner.nextDouble();
            scanner.nextLine();


            Material materiau = new Material(
                    UUID.randomUUID(),
                    nom,
                    coutUnitaire,
                    quantite,
                    TypeComposant.MATERIAU,
                    tauxTVA,
                    coutTransport,
                    coefficientQualite
            );


            materiauService.save(materiau);
            projet.ajouterMateriau(materiau);
            System.out.println("Matériau ajouté avec succès !");


            System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                continuer = false;
            }
        }
    }


    private void ajouterMainDoeuvre(Projet projet) throws SQLException {
        System.out.println("--- Ajout de la main-d'œuvre ---");
        boolean continuer = true;
        while (continuer) {
            System.out.print("Entrez le nom de la main-d'œuvre : ");
            String nom = scanner.nextLine();
            System.out.print("Entrez le coût unitaire de la main-d'œuvre : ");
            double coutUnitaire = scanner.nextDouble();
            System.out.print("Entrez la quantité (heures de travail) : ");
            double quantite = scanner.nextDouble();
            System.out.print("Entrez le taux de TVA : ");
            double tauxTVA = scanner.nextDouble();
            System.out.print("Entrez le nombre d'heures travaillées : ");
            double heuresTravail = scanner.nextDouble();
            System.out.print("Entrez le facteur de productivité : ");
            double productiviteOuvrier = scanner.nextDouble();
            scanner.nextLine();

            MainDoeuvre mainDoeuvre = new MainDoeuvre(
                    UUID.randomUUID(),
                    nom,
                    coutUnitaire,
                    quantite,
                    TypeComposant.MAIN_DOEUVRE,
                    tauxTVA,
                    heuresTravail,
                    productiviteOuvrier
            );

            mainDoeuvreService.save(mainDoeuvre);
            projet.ajouterMainDoeuvre(mainDoeuvre);
            System.out.println("Main-d'œuvre ajoutée avec succès !");
            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                continuer = false;
            }
        }
    }


    private void calculerEtEnregistrerCout(Projet projet) {
        System.out.println("--- Calcul du coût total ---");
        System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
        boolean appliquerTVA = scanner.nextLine().equalsIgnoreCase("y");
        if (appliquerTVA) {
            System.out.print("Entrez le pourcentage de TVA : ");
            projet.setTauxTVA(scanner.nextDouble());
            scanner.nextLine();
        }

        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        boolean appliquerMarge = scanner.nextLine().equalsIgnoreCase("y");
        if (appliquerMarge) {
            System.out.print("Entrez le pourcentage de marge bénéficiaire : ");
            projet.setMargeBeneficiaire(scanner.nextDouble());
            scanner.nextLine();
        }

        double coutTotal = projet.calculerCoutTotal();
        System.out.println("--- Résultat du Calcul ---");
        System.out.println("Nom du projet : " + projet.getNomProjet());
        System.out.println("Client : " + projet.getClient().getNom());

        System.out.println("Coût total final du projet : " + coutTotal + " €");

        System.out.println("--- Enregistrement du Devis ---");
        System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
        String dateEmission = scanner.nextLine();
        System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
        String dateValidite = scanner.nextLine();
        System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Devis enregistré avec succès !");
        }
        System.out.println("--- Fin du projet ---");
    }

    private void afficherProjets() {
        List<Projet> projets = projetService.getAllProjects();
        if (projets.isEmpty()) {
            System.out.println("Aucun projet trouvé.");
        } else {
            for (Projet projet : projets) {
                System.out.println(projet);
            }
        }
    }

    private void calculerCoutProjet() {
        System.out.println("--- Calculer le coût d'un projet ---");

    }

    private Client ajouterNouveauClient() throws SQLException {
        Client client = new Client();
        System.out.print("Entrez le nom du client : ");
        client.setNom(scanner.nextLine());
        System.out.print("Entrez l'adresse du client : ");
        client.setAdresse(scanner.nextLine());
        System.out.print("Entrez le numéro de téléphone du client : ");
        client.setTelephone(scanner.nextLine());
        clientService.save(client);
        System.out.println("Client ajouté avec succès !");
        return client;
    }

    private void afficherDetailsClient(Client client) {
        System.out.println("Nom : " + client.getNom());
        System.out.println("Adresse : " + client.getAdresse());
        System.out.println("Numéro de téléphone : " + client.getTelephone());
    }
}

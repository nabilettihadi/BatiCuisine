package main.java.com.baticuisine.ui;

import main.java.com.baticuisine.enums.EtatProjet;
import main.java.com.baticuisine.enums.TypeComposant;
import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.model.Projet;
import main.java.com.baticuisine.model.Devis;
import main.java.com.baticuisine.service.ClientService;
import main.java.com.baticuisine.service.ProjetService;
import main.java.com.baticuisine.service.ComposantService;
import main.java.com.baticuisine.service.DevisService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProjetUI {
    private final ProjetService projetService;
    private final ClientService clientService;
    private final ComposantService<Material> materiauService;
    private final ComposantService<MainDoeuvre> mainDoeuvreService;
    private final DevisService devisService;
    private final Scanner scanner;
    
    private static final int CHOIX_CREER_PROJET = 1;
    private static final int CHOIX_AFFICHER_PROJETS = 2;
    private static final int CHOIX_QUITTER = 0;

    public ProjetUI(ProjetService projetService, ClientService clientService,
                    ComposantService<Material> materiauService,
                    ComposantService<MainDoeuvre> mainDoeuvreService,
                    DevisService devisService) {
        this.projetService = projetService;
        this.clientService = clientService;
        this.materiauService = materiauService;
        this.mainDoeuvreService = mainDoeuvreService;
        this.devisService = devisService;
        this.scanner = new Scanner(System.in);
    }

    public void startProjetMenu() throws SQLException {
        System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        boolean continuer = true;

        while (continuer) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case CHOIX_CREER_PROJET:
                    creerNouveauProjet();
                    break;
                case CHOIX_AFFICHER_PROJETS:
                    afficherProjets();
                    break;
                case CHOIX_QUITTER:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void afficherMenuPrincipal() {
        System.out.println("=== Menu Principal ===");
        System.out.println("1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private void creerNouveauProjet() throws SQLException {
        System.out.println("--- Recherche de client ---");
        Client client = choisirClient();

        if (client != null) {
            Projet projet = initialiserNouveauProjet(client);
            try {
                projetService.createProject(projet);
                System.out.println("Projet enregistré avec succès !");
                calculerEtEnregistrerCout(projet);
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'enregistrement du projet : " + e.getMessage());
            }
        }
    }

    private Client choisirClient() throws SQLException {
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int choixClient = scanner.nextInt();
        scanner.nextLine();

        if (choixClient == 1) {
            return rechercherClientExist();
        } else if (choixClient == 2) {
            return ajouterNouveauClient();
        } else {
            System.out.println("Choix invalide.");
            return null;
        }
    }

    private Client rechercherClientExist() throws SQLException {
        System.out.print("--- Recherche de client existant ---\nEntrez le nom du client : ");
        String nomClient = scanner.nextLine();
        Client client = clientService.findByName(nomClient);

        if (client != null) {
            System.out.println("Client trouvé !");
            afficherDetailsClient(client);
            System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
            String confirmation = scanner.nextLine();
            if (!confirmation.equalsIgnoreCase("y")) {
                return null;
            }
            return client;
        } else {
            System.out.println("Client non trouvé !");
            return null;
        }
    }

    private Projet initialiserNouveauProjet(Client client) throws SQLException {
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
        projet.setEtatProjet(EtatProjet.EN_COURS);
        return projet;
    }

    private void ajouterMateriaux(Projet projet) throws SQLException {
        System.out.println("--- Ajout des matériaux ---");
        boolean continuer = true;

        while (continuer) {
            Material materiau = saisirMateriau();
            materiauService.save(materiau);
            projet.ajouterMateriau(materiau);
            System.out.println("Matériau ajouté avec succès !");

            System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
            continuer = scanner.nextLine().equalsIgnoreCase("y");
        }
    }

    private Material saisirMateriau() {
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

        return new Material(
                UUID.randomUUID(),
                nom,
                coutUnitaire,
                quantite,
                TypeComposant.MATERIAU,
                tauxTVA,
                coutTransport,
                coefficientQualite
        );
    }

    private void ajouterMainDoeuvre(Projet projet) throws SQLException {
        System.out.println("--- Ajout de la main-d'œuvre ---");
        boolean continuer = true;

        while (continuer) {
            MainDoeuvre mainDoeuvre = saisirMainDoeuvre();
            mainDoeuvreService.save(mainDoeuvre);
            projet.ajouterMainDoeuvre(mainDoeuvre);
            System.out.println("Main-d'œuvre ajoutée avec succès !");
            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            continuer = scanner.nextLine().equalsIgnoreCase("y");
        }
    }

    private MainDoeuvre saisirMainDoeuvre() {
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

        return new MainDoeuvre(
                UUID.randomUUID(),
                nom,
                coutUnitaire,
                quantite,
                TypeComposant.MAIN_DOEUVRE,
                tauxTVA,
                heuresTravail,
                productiviteOuvrier
        );
    }

    private void calculerEtEnregistrerCout(Projet projet) {
        System.out.println("--- Calcul du coût total ---");
        boolean appliquerTVA = demanderApplicationTVA(projet);
        boolean appliquerMarge = demanderApplicationMarge(projet);

        double coutTotal = projet.calculerCoutTotal();
        afficherResultatCalcul(projet, coutTotal);

        System.out.println("--- Enregistrement du Devis ---");
        enregistrerDevis(projet);
        System.out.println("--- Fin du projet ---");
    }

    private boolean demanderApplicationTVA(Projet projet) {
        System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
        boolean appliquerTVA = scanner.nextLine().equalsIgnoreCase("y");
        if (appliquerTVA) {
            System.out.print("Entrez le pourcentage de TVA : ");
            projet.setTauxTVA(scanner.nextDouble());
            scanner.nextLine();
        }
        return appliquerTVA;
    }

    private boolean demanderApplicationMarge(Projet projet) {
        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        boolean appliquerMarge = scanner.nextLine().equalsIgnoreCase("y");
        if (appliquerMarge) {
            System.out.print("Entrez le pourcentage de marge bénéficiaire : ");
            projet.setMargeBeneficiaire(scanner.nextDouble());
            scanner.nextLine();
        }
        return appliquerMarge;
    }

    private void afficherResultatCalcul(Projet projet, double coutTotal) {
        System.out.println("--- Résultat du Calcul ---");
        System.out.println("Nom du projet : " + projet.getNomProjet());
        System.out.println("Client : " + projet.getClient().getNom());
        System.out.println("Coût total final du projet : " + coutTotal + " €");
    }

    private void enregistrerDevis(Projet projet) {
        System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
        String dateEmission = scanner.nextLine();
        System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
        String dateValidite = scanner.nextLine();
        System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");

        if (scanner.nextLine().equalsIgnoreCase("y")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Devis devis = new Devis(
                    UUID.randomUUID(),
                    projet.getCoutTotal(),
                    LocalDate.parse(dateEmission, formatter),
                    LocalDate.parse(dateValidite, formatter),
                    false,
                    projet,
                    projet.getClient()
            );

            devisService.createDevis(devis);
            System.out.println("Devis enregistré avec succès !");
        }
    }

    private void afficherProjets() {
        List<Projet> projets = projetService.getAllProjects();
        if (projets.isEmpty()) {
            System.out.println("Aucun projet trouvé.");
        } else {
            System.out.println("=== Liste des Projets ===");
            for (Projet projet : projets) {
                System.out.println(projet.toString());
                System.out.println("---------------------------------------------------");
            }
        }
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

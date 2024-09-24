package main.java.com.baticuisine.ui;

import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.model.Devis;
import main.java.com.baticuisine.model.Projet;
import main.java.com.baticuisine.service.DevisService;
import main.java.com.baticuisine.service.ClientService;
import main.java.com.baticuisine.service.ProjetService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class DevisUI {
    private final DevisService devisService;
    private final ClientService clientService;
    private final ProjetService projetService;
    private final Scanner scanner;

    public DevisUI(DevisService devisService, ClientService clientService, ProjetService projetService) {
        this.devisService = devisService;
        this.clientService = clientService;
        this.projetService = projetService;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuDevis() {
        while (true) {
            System.out.println("=== Menu Devis ===");
            System.out.println("1. Créer un nouveau devis");
            System.out.println("2. Consulter un devis");
            System.out.println("3. Afficher tous les devis");
            System.out.println("4. Accepter un devis");
            System.out.println("5. Retourner au menu principal");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (choix) {
                    case 1:
                        creerDevis();
                        break;
                    case 2:
                        consulterDevis();
                        break;
                    case 3:
                        afficherTousLesDevis();
                        break;
                    case 4:
                        accepterDevis();
                        break;
                    case 5:
                        System.out.println("Retour au menu principal.");
                        return;
                    default:
                        System.out.println("Option non valide. Veuillez réessayer.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la manipulation du devis : " + e.getMessage());
            }
        }
    }

    private void creerDevis() throws SQLException {
        System.out.println("--- Création d'un Nouveau Devis ---");

        System.out.print("Entrez l'ID du client : ");
        String clientId = scanner.nextLine();
        Optional<Client> clientOpt = Optional.ofNullable(clientService.findById(UUID.fromString(clientId)));

        if (clientOpt.isEmpty()) {
            System.out.println("Client non trouvé.");
            return;
        }

        Client client = clientOpt.get();

        System.out.print("Entrez l'ID du projet : ");
        String projetId = scanner.nextLine();
        Optional<Projet> projetOpt = Optional.ofNullable(projetService.getProjectById(UUID.fromString(projetId)));

        if (projetOpt.isEmpty()) {
            System.out.println("Projet non trouvé.");
            return;
        }

        Projet projet = projetOpt.get();

        System.out.print("Entrez le montant estimé du devis : ");
        double montantEstime = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        System.out.print("Entrez la date d'émission du devis (jj/mm/aaaa) : ");
        String dateEmissionStr = scanner.nextLine();
        LocalDate dateEmission = LocalDate.parse(dateEmissionStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("Entrez la date de validité du devis (jj/mm/aaaa) : ");
        String dateValiditeStr = scanner.nextLine();
        LocalDate dateValidite = LocalDate.parse(dateValiditeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Devis devis = new Devis(UUID.randomUUID(), montantEstime, dateEmission, dateValidite, false, projet, client);
        devisService.createDevis(devis);

        System.out.println("Devis créé avec succès !");
    }

    private void consulterDevis() {
        System.out.print("Entrez l'ID du devis : ");
        String devisId = scanner.nextLine();

        try {
            UUID devisUuid = UUID.fromString(devisId);
            Optional<Devis> devisOpt = devisService.getDevisById(devisUuid);

            if (devisOpt.isPresent()) {
                Devis devis = devisOpt.get();
                System.out.println("--- Détail du Devis ---");
                System.out.println("ID: " + devis.getId());
                System.out.println("Montant estimé : " + devis.getMontantEstime());
                System.out.println("Date d'émission : " + devis.getDateEmission());
                System.out.println("Date de validité : " + devis.getDateValidite());
                System.out.println("Statut : " + (devis.isAccepte() ? "Accepté" : "Non accepté"));
                System.out.println("Projet : " + devis.getProjet().getNomProjet());
                System.out.println("Client : " + devis.getClient().getNom());
            } else {
                System.out.println("Devis non trouvé.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("L'ID du devis est invalide.");
        }
    }

    private void afficherTousLesDevis() {
        System.out.println("--- Liste des Devis ---");
        for (Devis devis : devisService.getAllDevis()) {
            System.out.println("ID: " + devis.getId() + " | Montant Estimé : " + devis.getMontantEstime() + " | Statut : " + (devis.isAccepte() ? "Accepté" : "Non accepté"));
        }
    }

    private void accepterDevis() {
        System.out.print("Entrez l'ID du devis que vous souhaitez accepter : ");
        String devisId = scanner.nextLine();

        try {
            UUID devisUuid = UUID.fromString(devisId);
            Optional<Devis> devisOpt = devisService.getDevisById(devisUuid);

            if (devisOpt.isPresent()) {
                Devis devis = devisOpt.get();
                if (!devis.isAccepte()) {
                    devis.setAccepte(true);
                    devisService.updateDevis(devis);
                    System.out.println("Le devis a été accepté avec succès !");
                } else {
                    System.out.println("Ce devis a déjà été accepté.");
                }
            } else {
                System.out.println("Devis non trouvé.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("L'ID du devis est invalide.");
        }
    }

}
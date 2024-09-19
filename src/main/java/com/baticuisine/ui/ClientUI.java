package main.java.com.baticuisine.ui;

import main.java.com.baticuisine.service.ClientService;
import main.java.com.baticuisine.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ClientUI {

    private final ClientService clientService;
    private final Scanner scanner;

    public ClientUI(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void startClientMenu() {
        while (true) {
            System.out.println("=== Menu de Gestion des Clients ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Rechercher un client par ID");
            System.out.println("4. Mettre à jour un client");
            System.out.println("5. Supprimer un client");
            System.out.println("0. Retourner au menu principal");
            System.out.print("Choisissez une option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addClient();
                    break;
                case 2:
                    displayAllClients();
                    break;
                case 3:
                    displayClientById();
                    break;
                case 4:
                    updateClient();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void addClient() {
        try {
            System.out.print("Entrez le nom du client: ");
            String nom = scanner.nextLine();
            System.out.print("Entrez l'adresse du client: ");
            String adresse = scanner.nextLine();
            System.out.print("Entrez le téléphone du client: ");
            String telephone = scanner.nextLine();
            System.out.print("Le client est-il professionnel (true/false): ");
            boolean estProfessionnel = scanner.nextBoolean();
            scanner.nextLine();

            UUID id = UUID.randomUUID();
            Client client = new Client(id, nom, adresse, telephone, estProfessionnel);
            clientService.save(client);

            System.out.println("Client ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du client: " + e.getMessage());
        }
    }

    private void displayAllClients() {
        try {
            List<Client> clients = clientService.findAll();
            if (clients.isEmpty()) {
                System.out.println("Aucun client trouvé.");
            } else {
                for (Client client : clients) {
                    System.out.println("ID: " + client.getId());
                    System.out.println("Nom: " + client.getNom());
                    System.out.println("Adresse: " + client.getAdresse());
                    System.out.println("Téléphone: " + client.getTelephone());
                    System.out.println("Est Professionnel: " + client.isEstProfessionnel());
                    System.out.println("---------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des clients: " + e.getMessage());
        }
    }

    private void displayClientById() {
        try {
            System.out.print("Entrez l'ID du client à rechercher (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());
            Client client = clientService.findById(id);
            if (client != null) {
                System.out.println("ID: " + client.getId());
                System.out.println("Nom: " + client.getNom());
                System.out.println("Adresse: " + client.getAdresse());
                System.out.println("Téléphone: " + client.getTelephone());
                System.out.println("Est Professionnel: " + client.isEstProfessionnel());
            } else {
                System.out.println("Client non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client: " + e.getMessage());
        }
    }

    private void updateClient() {
        try {
            System.out.print("Entrez l'ID du client à mettre à jour (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());
            Client client = clientService.findById(id);
            if (client != null) {
                System.out.print("Entrez le nouveau nom (actuel: " + client.getNom() + "): ");
                String nom = scanner.nextLine();
                System.out.print("Entrez la nouvelle adresse (actuelle: " + client.getAdresse() + "): ");
                String adresse = scanner.nextLine();
                System.out.print("Entrez le nouveau téléphone (actuel: " + client.getTelephone() + "): ");
                String telephone = scanner.nextLine();
                System.out.print("Le client est-il professionnel (actuel: " + client.isEstProfessionnel() + ") (true/false): ");
                boolean estProfessionnel = scanner.nextBoolean();
                scanner.nextLine();

                client.setNom(nom);
                client.setAdresse(adresse);
                client.setTelephone(telephone);
                client.setEstProfessionnel(estProfessionnel);
                clientService.update(client);

                System.out.println("Client mis à jour avec succès !");
            } else {
                System.out.println("Client non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du client: " + e.getMessage());
        }
    }

    private void deleteClient() {
        try {
            System.out.print("Entrez l'ID du client à supprimer (format UUID): ");
            UUID id = UUID.fromString(scanner.nextLine());
            clientService.delete(id);
            System.out.println("Client supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du client: " + e.getMessage());
        }
    }
}


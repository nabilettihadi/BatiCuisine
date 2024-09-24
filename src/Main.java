import main.java.com.baticuisine.database.DatabaseConnection;
import main.java.com.baticuisine.implR.*;
import main.java.com.baticuisine.implS.*;
import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.service.ClientService;
import main.java.com.baticuisine.service.ComposantService;
import main.java.com.baticuisine.service.DevisService;
import main.java.com.baticuisine.service.ProjetService;
import main.java.com.baticuisine.ui.ClientUI;
import main.java.com.baticuisine.ui.ComposantUI;
import main.java.com.baticuisine.ui.DevisUI;
import main.java.com.baticuisine.ui.ProjetUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            // Initialisation des repositories
            ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(connection);
            MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl(connection);
            MainDoeuvreRepositoryImpl mainDoeuvreRepository = new MainDoeuvreRepositoryImpl(connection);
            DevisRepositoryImpl devisRepository = new DevisRepositoryImpl(connection);
            ProjetRepositoryImpl projetRepository = new ProjetRepositoryImpl(connection);

            // Initialisation des services
            ClientService clientService = new ClientServiceImpl(clientRepository);
            ComposantService<Material> materialService = new MaterialServiceImpl(materialRepository);
            ComposantService<MainDoeuvre> mainDoeuvreService = new MainDoeuvreServiceImpl(mainDoeuvreRepository);
            DevisService devisService = new DevisServiceImpl(devisRepository);
            ProjetService projetService = new ProjetServiceImpl(projetRepository);

            // Initialisation des interfaces utilisateur
            ClientUI clientUI = new ClientUI(clientService);
            ComposantUI composantUI = new ComposantUI(mainDoeuvreService, materialService);
            DevisUI devisUI = new DevisUI(devisService, clientService, projetService);
            ProjetUI projetUI = new ProjetUI(projetService, clientService, materialService, mainDoeuvreService, devisService);

            // Menu principal
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Gestion des Clients");
                System.out.println("2. Gestion des Composants");
                System.out.println("3. Gestion des Projets");
                System.out.println("4. Gestion des Devis");
                System.out.println("0. Quitter");
                System.out.print("Choisissez une option: ");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        clientUI.startClientMenu();
                        break;
                    case 2:
                        composantUI.startComposantMenu();
                        break;
                    case 3:
                        projetUI.startProjetMenu();
                        break;
                    case 4:
                        devisUI.afficherMenuDevis();
                        break;
                    case 0:
                        System.out.println("Au revoir !");
                        return;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données: " + e.getMessage());
        }
    }
}

import main.java.com.baticuisine.database.DatabaseConnection;
import main.java.com.baticuisine.implR.MainDoeuvreRepositoryImpl;
import main.java.com.baticuisine.implS.ClientServiceImpl;
import main.java.com.baticuisine.implR.ClientRepositoryImpl;
import main.java.com.baticuisine.implR.MaterialRepositoryImpl;
import main.java.com.baticuisine.implS.MainDoeuvreServiceImpl;
import main.java.com.baticuisine.implS.MaterialServiceImpl;
import main.java.com.baticuisine.model.Composant;
import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.service.ClientService;
import main.java.com.baticuisine.service.ComposantService;
import main.java.com.baticuisine.ui.ClientUI;
import main.java.com.baticuisine.ui.ComposantUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {

            ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(connection);
            ClientService clientService = new ClientServiceImpl(clientRepository);
            ClientUI clientUI = new ClientUI(clientService);


            MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl(connection);
            MainDoeuvreRepositoryImpl mainDoeuvreRepository = new MainDoeuvreRepositoryImpl(connection);

            ComposantService<MainDoeuvre> mainDoeuvreService = new MainDoeuvreServiceImpl(mainDoeuvreRepository);
            ComposantService<Material> materialService = new MaterialServiceImpl(materialRepository);


            ComposantService<Composant> composantService = null;


            ComposantUI composantUI = new ComposantUI(mainDoeuvreService, materialService);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Gestion des Clients");
                System.out.println("2. Gestion des Composants");
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

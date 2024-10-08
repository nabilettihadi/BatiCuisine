import main.java.com.baticuisine.database.DatabaseConnection;
import main.java.com.baticuisine.implR.MainDoeuvreRepositoryImpl;
import main.java.com.baticuisine.implS.ClientServiceImpl;
import main.java.com.baticuisine.implR.ClientRepositoryImpl;
import main.java.com.baticuisine.implR.DevisRepositoryImpl;
import main.java.com.baticuisine.implS.DevisServiceImpl;
import main.java.com.baticuisine.implR.MaterialRepositoryImpl;
import main.java.com.baticuisine.implS.MainDoeuvreServiceImpl;
import main.java.com.baticuisine.implS.MaterialServiceImpl;
import main.java.com.baticuisine.implR.ProjetRepositoryImpl;
import main.java.com.baticuisine.implS.ProjetServiceImpl;
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

            // Initialisation des repositories et services
            ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(connection);
            ClientService clientService = new ClientServiceImpl(clientRepository);
            ClientUI clientUI = new ClientUI(clientService);

            MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl(connection);
            MainDoeuvreRepositoryImpl mainDoeuvreRepository = new MainDoeuvreRepositoryImpl(connection);

            ComposantService<MainDoeuvre> mainDoeuvreService = new MainDoeuvreServiceImpl(mainDoeuvreRepository);
            ComposantService<Material> materialService = new MaterialServiceImpl(materialRepository);

            ComposantUI composantUI = new ComposantUI(mainDoeuvreService, materialService);

            // Initialisation des Devis
            DevisRepositoryImpl devisRepository = new DevisRepositoryImpl(connection);
            DevisService devisService = new DevisServiceImpl(devisRepository);


            ProjetRepositoryImpl projetRepository = new ProjetRepositoryImpl(connection);
            ProjetService projetService = new ProjetServiceImpl(projetRepository);
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
                        devisUI.afficherMenuDevis();  // Appel du menu des devis
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

package main.java.com.baticuisine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    private static DatabaseConnection instance;
    private Connection connection;


    private final String url = "jdbc:postgresql://localhost:5432/BatiCuisine";
    private final String username = "postgres";
    private final String password = "2002";


    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erreur de chargement du driver PostgreSQL");
            ex.printStackTrace();
        }
    }


    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}

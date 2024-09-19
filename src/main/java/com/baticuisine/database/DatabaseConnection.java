package main.java.com.baticuisine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;

    private DatabaseConnection() throws SQLException {
        try {

            String url = "jdbc:postgresql://localhost:5432/BatiCuisine";
            String username = "postgres";
            String password = "2002";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la connexion à la base de données", e);
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

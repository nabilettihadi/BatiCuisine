package main.java.com.baticuisine.implR;

import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.repository.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepositoryImpl implements ClientRepository {
    private final Connection connection;

    public ClientRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Client client) throws SQLException {
        String query = "INSERT INTO client (id, nom, adresse, telephone, Professionnel) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getAdresse());
            stmt.setString(4, client.getTelephone());
            stmt.setBoolean(5, client.isEstProfessionnel());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                String telephone = rs.getString("telephone");
                boolean estProfessionnel = rs.getBoolean("Professionnel");
                clients.add(new Client(id, nom, adresse, telephone, estProfessionnel));
            }
        }
        return clients;
    }

    @Override
    public Client findById(UUID id) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String adresse = rs.getString("adresse");
                    String telephone = rs.getString("telephone");
                    boolean estProfessionnel = rs.getBoolean("Professionnel");
                    return new Client(id, nom, adresse, telephone, estProfessionnel);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Client client) throws SQLException {
        String query = "UPDATE client SET nom = ?, adresse = ?, telephone = ?, Professionnel = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getAdresse());
            stmt.setString(3, client.getTelephone());
            stmt.setBoolean(4, client.isEstProfessionnel());
            stmt.setObject(5, client.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(UUID id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Client findByName(String nomClient) throws SQLException {
        String query = "SELECT * FROM client WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomClient);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UUID id = (UUID) rs.getObject("id");
                    String adresse = rs.getString("adresse");
                    String telephone = rs.getString("telephone");
                    boolean estProfessionnel = rs.getBoolean("Professionnel");
                    return new Client(id, nomClient, adresse, telephone, estProfessionnel);
                }
            }
        }
        return null;
    }

}

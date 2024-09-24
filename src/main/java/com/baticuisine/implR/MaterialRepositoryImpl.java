package main.java.com.baticuisine.implR;

import main.java.com.baticuisine.enums.TypeComposant;
import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.repository.ComposantRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaterialRepositoryImpl implements ComposantRepository<Material> {
    private final Connection connection;

    public MaterialRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Material material) throws SQLException {
        String query = "INSERT INTO materiau (id, nom, cout_unitaire, quantite, type_composant, taux_tva, cout_transport, coefficient_qualite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, material.getId());
            stmt.setString(2, material.getNom());
            stmt.setDouble(3, material.getCoutUnitaire());
            stmt.setDouble(4, material.getQuantite());
            stmt.setObject(5, material.getTypeComposant().name(), Types.OTHER);
            stmt.setDouble(6, material.getTauxTVA());
            stmt.setDouble(7, material.getCoutTransport());
            stmt.setDouble(8, material.getCoefficientQualite());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Material> findAll() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM materiau";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("nom");
                double coutUnitaire = rs.getDouble("cout_unitaire");
                double quantite = rs.getDouble("quantite");
                TypeComposant typeComposant = TypeComposant.valueOf(rs.getString("type_composant"));
                double tauxTVA = rs.getDouble("taux_tva");
                double coutTransport = rs.getDouble("cout_transport");
                double coefficientQualite = rs.getDouble("coefficient_qualite");
                materials.add(new Material(id, nom, coutUnitaire, quantite,typeComposant, tauxTVA, coutTransport, coefficientQualite));
            }
        }
        return materials;
    }

    @Override
    public Material findById(UUID id) throws SQLException {
        String query = "SELECT * FROM materiau WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    double coutUnitaire = rs.getDouble("cout_unitaire");
                    double quantite = rs.getDouble("quantite");
                    TypeComposant typeComposant = TypeComposant.valueOf(rs.getString("type_composant"));
                    double tauxTVA = rs.getDouble("taux_tva");
                    double coutTransport = rs.getDouble("cout_transport");
                    double coefficientQualite = rs.getDouble("coefficient_qualite");
                    return new Material(id, nom, coutUnitaire, quantite, typeComposant, tauxTVA, coutTransport, coefficientQualite);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Material material) throws SQLException {
        String query = "UPDATE materiau SET nom = ?, cout_unitaire = ?, quantite = ?, type_composant = ?, taux_tva = ?, cout_transport = ?, coefficient_qualite = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, material.getNom());
            stmt.setDouble(2, material.getCoutUnitaire());
            stmt.setDouble(3, material.getQuantite());
            stmt.setObject(4, material.getTypeComposant().name(),Types.OTHER);
            stmt.setDouble(5, material.getTauxTVA());
            stmt.setDouble(6, material.getCoutTransport());
            stmt.setDouble(7, material.getCoefficientQualite());
            stmt.setObject(8, material.getId());
            stmt.executeUpdate();
        }
    }


    @Override
    public void delete(UUID id) throws SQLException {
        String query = "DELETE FROM materiau WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            int affectedRows = stmt.executeUpdate();
            if  (affectedRows == 0){
                throw new RuntimeException("no row affected when delete the materiels");
            }
        }
    }
}

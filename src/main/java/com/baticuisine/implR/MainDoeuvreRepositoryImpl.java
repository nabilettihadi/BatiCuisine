package main.java.com.baticuisine.implR;

import main.java.com.baticuisine.enums.TypeComposant;
import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.repository.ComposantRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainDoeuvreRepositoryImpl implements ComposantRepository<MainDoeuvre> {
    private final Connection connection;

    public MainDoeuvreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(MainDoeuvre mainDoeuvre) throws SQLException {
        String query = "INSERT INTO maindoeuvre (id, nom, cout_unitaire, quantite, type_composant, taux_tva, heures_travail, productivite_ouvrier) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, mainDoeuvre.getId());
            stmt.setString(2, mainDoeuvre.getNom());
            stmt.setDouble(3, mainDoeuvre.getCoutUnitaire());
            stmt.setDouble(4, mainDoeuvre.getQuantite());
            stmt.setObject(5, mainDoeuvre.getTypeComposant().name(), Types.OTHER);
            stmt.setDouble(6, mainDoeuvre.getTauxTVA());
            stmt.setDouble(7, mainDoeuvre.getHeuresTravail());
            stmt.setDouble(8, mainDoeuvre.getProductiviteOuvrier());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<MainDoeuvre> findAll() throws SQLException {
        List<MainDoeuvre> mainDoeuvres = new ArrayList<>();
        String query = "SELECT * FROM maindoeuvre";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("nom");
                double coutUnitaire = rs.getDouble("cout_unitaire");
                double quantite = rs.getDouble("quantite");
                TypeComposant typeComposant = TypeComposant.valueOf(rs.getString("type_composant"));
                double tauxTVA = rs.getDouble("taux_tva");
                double heuresTravail = rs.getDouble("heures_travail");
                double productiviteOuvrier = rs.getDouble("productivite_ouvrier");
                mainDoeuvres.add(new MainDoeuvre(id, nom, coutUnitaire, quantite, typeComposant, tauxTVA, heuresTravail, productiviteOuvrier));
            }
        }
        return mainDoeuvres;
    }

    @Override
    public MainDoeuvre findById(UUID id) throws SQLException {
        String query = "SELECT * FROM maindoeuvre WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    double coutUnitaire = rs.getDouble("cout_unitaire");
                    double quantite = rs.getDouble("quantite");
                    TypeComposant typeComposant = TypeComposant.valueOf(rs.getString("type_composant"));
                    double tauxTVA = rs.getDouble("taux_tva");
                    double heuresTravail = rs.getDouble("heures_travail");
                    double productiviteOuvrier = rs.getDouble("productivite_ouvrier");
                    return new MainDoeuvre(id, nom, coutUnitaire, quantite, typeComposant, tauxTVA, heuresTravail, productiviteOuvrier);
                }
            }
        }
        return null;
    }

    @Override
    public void update(MainDoeuvre composant) throws SQLException {
        String query = "UPDATE maindoeuvre SET nom = ?, cout_unitaire = ?, quantite = ?, type_Composant = ?, taux_tva = ?, heures_travail = ?, productivite_ouvrier = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, composant.getNom());
            stmt.setDouble(2, composant.getCoutUnitaire());
            stmt.setDouble(3, composant.getQuantite());
            stmt.setObject( 4,composant.getTypeComposant().name(), Types.OTHER);
            stmt.setDouble(5, composant.getTauxTVA());
            stmt.setDouble(6, composant.getHeuresTravail());
            stmt.setDouble(7, composant.getProductiviteOuvrier());
            stmt.setObject(8, composant.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(UUID id) throws SQLException {
        String query = "DELETE FROM maindoeuvre WHERE id = ? AND type_composant = 'MAIN_DOEUVRE'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }
}

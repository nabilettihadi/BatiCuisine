package main.java.com.baticuisine.implR;

import main.java.com.baticuisine.enums.EtatProjet;
import main.java.com.baticuisine.model.Projet;
import main.java.com.baticuisine.repository.ProjetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjetRepositoryImpl implements ProjetRepository {
    private final Connection connection;

    public ProjetRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Projet projet) throws SQLException {
        String sql = "INSERT INTO projet (id, nom_projet, marge_beneficiaire, cout_total, etat_projet, client_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, projet.getId());
            statement.setString(2, projet.getNomProjet());
            statement.setDouble(3, projet.getMargeBeneficiaire());
            statement.setDouble(4, projet.getCoutTotal());
            statement.setObject(5, projet.getEtatProjet().name(), Types.OTHER);
            statement.setObject(6, projet.getClient().getId());
            statement.executeUpdate();
        }
    }



    @Override
    public List<Projet> findAll() {
        List<Projet> projets = new ArrayList<>();
        String sql = "SELECT * FROM projet";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Projet projet = new Projet();
                projet.setId((UUID) resultSet.getObject("id"));
                projet.setNomProjet(resultSet.getString("nom_projet"));
                projet.setMargeBeneficiaire(resultSet.getDouble("marge_beneficiaire"));
                projet.setCoutTotal(resultSet.getDouble("cout_total"));
                projet.setEtatProjet(EtatProjet.valueOf(resultSet.getString("etat_projet")));
                projets.add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projets;
    }

    @Override
    public Projet findById(UUID id) {
        Projet projet = null;
        String sql = "SELECT * FROM projet WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    projet = new Projet();
                    projet.setId((UUID) resultSet.getObject("id"));
                    projet.setNomProjet(resultSet.getString("nom_projet"));
                    projet.setMargeBeneficiaire(resultSet.getDouble("marge_beneficiaire"));
                    projet.setCoutTotal(resultSet.getDouble("cout_total"));
                    projet.setEtatProjet(EtatProjet.valueOf(resultSet.getString("etat_projet")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projet;
    }

    @Override
    public void update(Projet projet) {
        String sql = "UPDATE projet SET nom_projet = ?, marge_beneficiaire = ?, cout_total = ?, etat_projet = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, projet.getNomProjet());
            statement.setDouble(2, projet.getMargeBeneficiaire());
            statement.setDouble(3, projet.getCoutTotal());
            statement.setObject(4, projet.getEtatProjet().name(), Types.OTHER);
            statement.setObject(5, projet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM projet WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


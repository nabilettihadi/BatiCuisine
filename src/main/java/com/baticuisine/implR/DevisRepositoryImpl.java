package main.java.com.baticuisine.implR;

import main.java.com.baticuisine.model.Devis;
import main.java.com.baticuisine.repository.DevisRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DevisRepositoryImpl implements DevisRepository {
    private final Connection connection;

    public DevisRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Devis> findById(UUID id) {
        String sql = "SELECT * FROM devis WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Devis devis = mapResultSetToDevis(rs);
                return Optional.of(devis);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
        }
        return Optional.empty();
    }

    @Override
    public List<Devis> findAll() {
        List<Devis> devisList = new ArrayList<>();
        String sql = "SELECT * FROM devis";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Devis devis = mapResultSetToDevis(rs);
                devisList.add(devis);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
        }
        return devisList;
    }

    @Override
    public void save(Devis devis) {
        String sql = "INSERT INTO devis (id, montant_estime, date_emission, date_validite, accepte, projet_id, client_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, devis.getId());
            preparedStatement.setDouble(2, devis.getMontantEstime());
            preparedStatement.setDate(3, java.sql.Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(devis.getDateValidite()));
            preparedStatement.setBoolean(5, devis.isAccepte());
            preparedStatement.setObject(6, devis.getProjet().getId());
            preparedStatement.setObject(7, devis.getClient().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
        }
    }

    @Override
    public void update(Devis devis) {
        String sql = "UPDATE devis SET montant_estime = ?, date_emission = ?, date_validite = ?, accepte = ?, projet_id = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, devis.getMontantEstime());
            preparedStatement.setDate(2, java.sql.Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(devis.getDateValidite()));
            preparedStatement.setBoolean(4, devis.isAccepte());
            preparedStatement.setObject(5, devis.getProjet().getId());
            preparedStatement.setObject(6, devis.getClient().getId());
            preparedStatement.setObject(7, devis.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM devis WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Devis mapResultSetToDevis(ResultSet rs) throws SQLException {
        return new Devis(
                (UUID) rs.getObject("id"),
                rs.getDouble("montant_estime"),
                rs.getDate("date_emission").toLocalDate(),
                rs.getDate("date_validite").toLocalDate(),
                rs.getBoolean("accepte"),
                null,
                null
        );
    }
}

package main.java.com.baticuisine.repository;

import main.java.com.baticuisine.model.Composant;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ComposantRepository<T> {
    void save(T composant) throws SQLException;
    List<T> findAll() throws SQLException;
    T findById(UUID id) throws SQLException;
    void update(T composant) throws SQLException;
    void delete(UUID id) throws SQLException;
}

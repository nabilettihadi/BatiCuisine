package main.java.com.baticuisine.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ComposantService<T> {
    void save(T composant) throws SQLException;
    List<T> findAll() throws SQLException;
    T findById(UUID id) throws SQLException;
    void update(T composant) throws SQLException;
    boolean delete(UUID id) throws SQLException;
}

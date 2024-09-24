package main.java.com.baticuisine.repository;


import main.java.com.baticuisine.model.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ProjetRepository {
    void save(Projet projet) throws SQLException;
    List<Projet> findAll();
    Projet findById(UUID id);
    void update(Projet projet);
    void delete(UUID id);
}

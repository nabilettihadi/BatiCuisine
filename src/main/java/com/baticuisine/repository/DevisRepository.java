package main.java.com.baticuisine.repository;

import main.java.com.baticuisine.model.Devis;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DevisRepository {
    Optional<Devis> findById(UUID id);
    List<Devis> findAll();
    void save(Devis devis);
    void update(Devis devis);
    void delete(UUID id);
}

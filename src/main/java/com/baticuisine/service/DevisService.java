package main.java.com.baticuisine.service;

import main.java.com.baticuisine.model.Devis;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DevisService {
    Optional<Devis> getDevisById(UUID id);
    List<Devis> getAllDevis();
    void createDevis(Devis devis);
    void updateDevis(Devis devis);
    void deleteDevis(UUID id);
}

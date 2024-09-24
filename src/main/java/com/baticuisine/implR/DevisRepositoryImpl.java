package main.java.com.baticuisine.implR;

import main.java.com.baticuisine.model.Devis;
import main.java.com.baticuisine.repository.DevisRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DevisRepositoryImpl implements DevisRepository {
    private final List<Devis> devisList = new ArrayList<>();
    private final Connection connection;

    public DevisRepositoryImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<Devis> findById(UUID id) {
        return devisList.stream().filter(devis -> devis.getId().equals(id)).findFirst();
    }

    @Override
    public List<Devis> findAll() {
        return new ArrayList<>(devisList);
    }

    @Override
    public void save(Devis devis) {
        devisList.add(devis);
    }

    @Override
    public void update(Devis devis) {
        findById(devis.getId()).ifPresent(d -> {
            d.setMontantEstime(devis.getMontantEstime());
            d.setDateEmission(devis.getDateEmission());
            d.setDateValidite(devis.getDateValidite());
            d.setAccepte(devis.isAccepte());
        });
    }

    @Override
    public void delete(UUID id) {
        devisList.removeIf(devis -> devis.getId().equals(id));
    }
}

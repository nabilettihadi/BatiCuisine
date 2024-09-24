package main.java.com.baticuisine.implS;

import main.java.com.baticuisine.model.Devis;
import main.java.com.baticuisine.repository.DevisRepository;
import main.java.com.baticuisine.service.DevisService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DevisServiceImpl implements DevisService {
    private final DevisRepository devisRepository;

    public DevisServiceImpl(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    @Override
    public Optional<Devis> getDevisById(UUID id) {
        return devisRepository.findById(id);
    }

    @Override
    public List<Devis> getAllDevis() {
        return devisRepository.findAll();
    }

    @Override
    public void createDevis(Devis devis) {
        devisRepository.save(devis);
    }

    @Override
    public void updateDevis(Devis devis) {
        devisRepository.update(devis);
    }

    @Override
    public void deleteDevis(UUID id) {
        devisRepository.delete(id);
    }
}

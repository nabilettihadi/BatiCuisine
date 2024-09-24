package main.java.com.baticuisine.implS;

import main.java.com.baticuisine.model.Projet;
import main.java.com.baticuisine.repository.ProjetRepository;
import main.java.com.baticuisine.service.ProjetService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ProjetServiceImpl implements ProjetService {
    private final ProjetRepository projetRepository;

    public ProjetServiceImpl(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @Override
    public void createProject(Projet projet) throws SQLException {
        projetRepository.save(projet);
    }

    @Override
    public List<Projet> getAllProjects() {
        return projetRepository.findAll();
    }

    @Override
    public Projet getProjectById(UUID id) {
        return projetRepository.findById(id);
    }

    @Override
    public void updateProject(Projet projet) {
        projetRepository.update(projet);
    }

    @Override
    public void deleteProject(UUID id) {
        projetRepository.delete(id);
    }
}

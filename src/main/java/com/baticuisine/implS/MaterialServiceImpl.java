package main.java.com.baticuisine.implS;

import main.java.com.baticuisine.model.Material;
import main.java.com.baticuisine.repository.ComposantRepository;
import main.java.com.baticuisine.service.ComposantService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MaterialServiceImpl implements ComposantService<Material> {
    private final ComposantRepository<Material> repository;

    public MaterialServiceImpl(ComposantRepository<Material> repository) {
        this.repository = repository;
    }

    @Override
    public void save(Material composant) throws SQLException {
        repository.save(composant);
    }

    @Override
    public List<Material> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public Material findById(UUID id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public void update(Material composant) throws SQLException {
        repository.update(composant);
    }

    @Override
    public boolean delete(UUID id) throws SQLException {
        try {
            repository.delete(id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

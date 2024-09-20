package main.java.com.baticuisine.implS;

import main.java.com.baticuisine.model.MainDoeuvre;
import main.java.com.baticuisine.repository.ComposantRepository;
import main.java.com.baticuisine.service.ComposantService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MainDoeuvreServiceImpl implements ComposantService<MainDoeuvre> {
    private final ComposantRepository<MainDoeuvre> repository;

    public MainDoeuvreServiceImpl(ComposantRepository<MainDoeuvre> repository) {
        this.repository = repository;
    }

    @Override
    public void save(MainDoeuvre composant) throws SQLException {
        repository.save(composant);
    }

    @Override
    public List<MainDoeuvre> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public MainDoeuvre findById(UUID id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public void update(MainDoeuvre composant) throws SQLException {
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

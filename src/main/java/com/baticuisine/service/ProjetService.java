package main.java.com.baticuisine.service;

import main.java.com.baticuisine.model.Projet;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ProjetService {
    void createProject(Projet projet) throws SQLException;
    List<Projet> getAllProjects();
    Projet getProjectById(UUID id);
    void updateProject(Projet projet);
    void deleteProject(UUID id);
}

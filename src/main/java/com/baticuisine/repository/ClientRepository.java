package main.java.com.baticuisine.repository;

import main.java.com.baticuisine.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ClientRepository {
    void save(Client client) throws SQLException;
    List<Client> findAll() throws SQLException;
    Client findById(UUID id) throws SQLException;
    void update(Client client) throws SQLException;
    void delete(UUID id) throws SQLException;
    Client findByName(String nomClient) throws SQLException;
}


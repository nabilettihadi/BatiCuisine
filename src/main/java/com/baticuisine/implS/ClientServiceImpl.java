package main.java.com.baticuisine.implS;

import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.repository.ClientRepository;
import main.java.com.baticuisine.service.ClientService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(Client client) throws SQLException {
        clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() throws SQLException {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(UUID id) throws SQLException {
        return clientRepository.findById(id);
    }

    @Override
    public void update(Client client) throws SQLException {
        clientRepository.update(client);
    }

    @Override
    public void delete(UUID id) throws SQLException {
        clientRepository.delete(id);
    }
}


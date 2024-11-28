package com.imt.projet.Banque.infrastructure;

import com.imt.projet.Banque.interfaces.database.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.imt.projet.Banque.domain.model.Client.Clients;
import com.imt.projet.Banque.infrastructure.mapping.ClientsMongoAdapter;
import com.imt.projet.Banque.infrastructure.model.ClientsMongo;

import java.util.List;
import java.util.UUID;

@Repository
public class MongoClientRepository implements ClientRepository {

    ClientsMongoAdapter clientAdapter = new ClientsMongoAdapter();

    @Autowired
    private MongoClientSpringRepository mongoClientSpringRepository;

    @Override
    public boolean clientExists(UUID clientId) {
        return mongoClientSpringRepository.existsById(clientId);
    }

    @Override
    public boolean clientExists(String nom, String prenom, String genre) {
        return mongoClientSpringRepository.existsByProperties(nom, prenom, genre);
    }

    @Override
    public List<Clients> getAllClients() {
        return clientAdapter.toDomain(mongoClientSpringRepository.findAll());
    }

    @Override
    public Clients getClientByUUID(UUID clientId) {
        return clientAdapter.toDomain(mongoClientSpringRepository.findById(clientId).orElse(null));
    }

    @Override
    public void saveClient(Clients client) {
        mongoClientSpringRepository.save(clientAdapter.toMongo(client));
    }

    @Override
    public void deleteClient(UUID clientId) {
        mongoClientSpringRepository.deleteById(clientId);
    }
}

interface MongoClientSpringRepository extends MongoRepository<ClientsMongo, UUID> {
    @Query("{'nom': ?0, 'prenom': ?1, 'genre': ?2}")
    boolean existsByProperties(String nom, String prenom, String genre);
}

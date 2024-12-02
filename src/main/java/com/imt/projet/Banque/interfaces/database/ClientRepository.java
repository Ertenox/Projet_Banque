package com.imt.projet.Banque.interfaces.database;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.imt.projet.Banque.domain.model.Client.Clients;

@Repository
public interface ClientRepository {

    boolean clientExists(UUID clientId);
    boolean clientExists(String nom, String prenom, String genre);
    Clients getClientByUUID(UUID clientId);
    List<Clients> getAllClients();
    void saveClient(Clients client);
    void deleteClient(UUID clientId);
}

package com.imt.projet.Banque.infrastructure.mapping;

import java.util.ArrayList;
import java.util.List;

import com.imt.projet.Banque.domain.model.Client.Clients;
import com.imt.projet.Banque.infrastructure.model.ClientsMongo;

public class ClientsMongoAdapter {
    ContratMongoAdapter contratAdapter = new ContratMongoAdapter();

    public Clients toDomain(ClientsMongo client) {
        return new Clients(client.getId(), client.getNom(), client.getPrenom(), client.getGenre(), client.getContrats());
    }

    public List<Clients> toDomain(List<ClientsMongo> clients) {
        if (clients == null) {
            return null;
        }
        List<Clients> clientsDomain = new ArrayList<>();
        for (ClientsMongo client : clients) {
            clientsDomain.add(toDomain(client));
        }
        return clientsDomain;
    }

    public ClientsMongo toMongo(Clients client) {
        return new ClientsMongo(client.getClientId(), client.getNom(), client.getPrenom(), client.getGenre(), client.getContrats());
    }

    public List<ClientsMongo> toMongo(List<Clients> clients) {
        if (clients == null) {
            return null;
        }
        List<ClientsMongo> clientsMongo = new ArrayList<>();
        for (Clients client : clients) {
            clientsMongo.add(toMongo(client));
        }
        return clientsMongo;

    }

    
}

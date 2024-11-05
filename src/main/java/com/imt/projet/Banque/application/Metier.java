package com.imt.projet.Banque.application;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;
import com.imt.projet.Banque.domain.ContratFactory;
import com.imt.projet.Banque.domain.Contrats.Contrat;

public class Metier {

    private Map<UUID, Clients> clients = new HashMap<>();
    private Map<UUID, Contrat> contrats = new HashMap<>();

    public boolean clientExists(UUID clientId) {
        return clients.containsKey(clientId);
    }

    public boolean clientExists(String nom, String prenom, String genre) {
        for (Clients client : clients.values()) {
            if (client.getNom().equals(nom) && client.getPrenom().equals(prenom) && client.getGenre().equals(genre)) {
            return true;
            }
        }
        return false;
    }

    public Clients getClientsByUUID(UUID clientId) {
        return clients.get(clientId);
    }

    public Contrat getContratsByUUID(UUID contratId) {
        return contrats.get(contratId);
    }

    public String getClients() {
        return clients.values().toString();
    }
    
    public Contrat createContrats(UUID clientId, String type) throws Exception {
        if (!clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        Contrat contrat = ContratFactory.creerContrat(type, getClientsByUUID(clientId) );
        contrats.put(contrat.getId(), contrat);
        return contrat;
    }

    public void createClient(String nom, String prenom, String genre) throws Exception {
        genre = genre.isEmpty() ? "Inconnu" : genre;
        Clients client = new Clients(nom, prenom, genre);
        clients.put(client.getId(), client);
    }

    public void deleteClient(UUID clientId) throws Exception {
        if (!clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        if ((getClientsByUUID(clientId)).getContrats().size() > 0) {
            throw new Exception("Client has contracts");
        }
        clients.remove(clientId);
    }

    public void deleteContrat(UUID contratId) throws Exception {
        if (!contrats.containsKey(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        contrats.remove(contratId);
    }


}
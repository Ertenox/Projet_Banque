package com.imt.projet.Banque.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;
import com.imt.projet.Banque.domain.Contrats.*;
import com.imt.projet.Banque.model.*;
import com.imt.projet.Banque.model.Client.ClientOutput;
import com.imt.projet.Banque.model.Contrat.ContratEssential;
import com.imt.projet.Banque.model.Contrat.ContratOutput;
import com.imt.projet.Banque.model.Contrat.Type.CompteCourantOutput;
import com.imt.projet.Banque.model.Contrat.Type.CompteEpargneOutput;
import com.imt.projet.Banque.model.Contrat.*;
import com.imt.projet.Banque.model.Contrat.Type.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Service
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

    public ClientOutput getClientByUUIDOutput(UUID clientId) {
        Clients client = clients.get(clientId);
        if (client == null) {
            return null; 
        }
    
        ClientOutput clientOutput = new ClientOutput();
        clientOutput.setClientId(client.getClientId());
        clientOutput.setNom(client.getNom());
        clientOutput.setPrenom(client.getPrenom());
        clientOutput.setGenre(client.getGenre());
    
        List<ContratEssential> contratEssentials = new ArrayList<>();
        if (client.getContrats() != null) {
            for (Contrat contrat : client.getContrats()) {
                ContratEssential contratEssential = new ContratEssential();
                contratEssential.setId(contrat.getContratId());
                contratEssential.setType(contrat.getType());
                contratEssentials.add(contratEssential);
            }
        }
    
        clientOutput.setContrats(contratEssentials);
        return clientOutput;
    }

    public Clients getClientByUUID(UUID clientId) {
        return clients.get(clientId);
    }
    
    public List<ClientOutput> getClients() {
        List<ClientOutput> clientOutputs = new ArrayList<>();
        for (UUID clientId : clients.keySet()) {
            ClientOutput clientOutput = getClientByUUIDOutput(clientId);
            if (clientOutput != null) {
                clientOutputs.add(clientOutput);
            }
        }
        return clientOutputs;
    }
    
    public void createClient(String nom, String prenom, String genre) throws Exception {
        genre = genre.isEmpty() ? "Inconnu" : genre;
        Clients client = new Clients(nom, prenom, genre);
        clients.put(client.getClientId(), client);
    }

    public void deleteClient(UUID clientId) throws Exception {
        if (!clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        if ((getClientByUUID(clientId)).getContrats() != null) {
            throw new Exception("Client has contracts");
        }
        clients.remove(clientId);
    }

    public void patchClient(UUID clientId, Map<String, String> patchData) throws Exception {
        if (!clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        Clients client = getClientByUUID(clientId);
        if (patchData.containsKey("nom")) {
            client.setNom(patchData.get("nom"));
        }
        if (patchData.containsKey("prenom")) {
            client.setPrenom(patchData.get("prenom"));
        }
        if (patchData.containsKey("genre")) {
            client.setGenre(patchData.get("genre"));
        }
    }
    

    public boolean contratExists(UUID contratId) {
        if (contrats.containsKey(contratId)) {
            return true;
        }
        return false;
    }

    public Contrat getContratByUUID(UUID contratId) {
        return contrats.get(contratId);
    }

    public ContratOutput getContratByUUIDOutput(UUID contratId) {
        Contrat contrat = contrats.get(contratId);
        if (contrat == null) {
            return null;
        }
    
        if (contrat instanceof CompteCourant) {
            CompteCourant compteCourant = (CompteCourant) contrat;
            return new CompteCourantOutput(
                compteCourant.getContratId(),
                compteCourant.getType(),
                compteCourant.getDate(),
                compteCourant.getBalance(),
                compteCourant.getClient().getClientId()
            );
        } else if (contrat instanceof CompteEpargne) {
            CompteEpargne compteEpargne = (CompteEpargne) contrat;
            return new CompteEpargneOutput(
                compteEpargne.getContratId(),
                compteEpargne.getType(),
                compteEpargne.getDate(),
                compteEpargne.getInteret(),
                compteEpargne.getBalance(),
                compteEpargne.getClient().getClientId(),
                compteEpargne.isStatus()
            );
        }
    
        return null;
    }
    
    public List<ContratOutput> getAllContrats() {
        List<ContratOutput> contratsOutput = new ArrayList<>();
        for (Contrat contrat : contrats.values()) {
            ContratOutput contratOutput = getContratByUUIDOutput(contrat.getContratId());
            if (contratOutput != null) {
                contratsOutput.add(contratOutput);
            }
        }
        return contratsOutput;
    }

    public Contrat createContrat(UUID clientId, String type, Double balance) throws Exception {
        if (!clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        Contrat contrat = ContratFactory.creerContrat(type, getClientByUUID(clientId), balance);
        contrats.put(contrat.getContratId(), contrat);
        return contrat;
    }

    public void deleteContrat(UUID contratId) throws Exception {
        if (!contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        contrats.remove(contratId);
    }

    public void patchContrat(UUID contratId, Map<String, String> patchData) throws Exception {
        if (!contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        Contrat contrat = getContratByUUID(contratId);
        if (patchData.containsKey("balance")) {
            contrat.updateBalance(Double.parseDouble(patchData.get("balance")));
        }
    }


}
package com.imt.projet.Banque.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.imt.projet.Banque.domain.Contrats.*;
import com.imt.projet.Banque.domain.model.Client.ClientOutput;
import com.imt.projet.Banque.domain.model.Client.Clients;
import com.imt.projet.Banque.domain.model.Contrat.ContratEssential;
import com.imt.projet.Banque.domain.model.Contrat.ContratOutput;
import com.imt.projet.Banque.domain.model.Contrat.Type.CompteCourantOutput;
import com.imt.projet.Banque.domain.model.Contrat.Type.CompteEpargneOutput;

import com.imt.projet.Banque.interfaces.database.ClientRepository;
import com.imt.projet.Banque.interfaces.database.ContratRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Metier {

    @Autowired
    private ClientRepository clientRepository; 
    @Autowired
    private ContratRepository contratRepository;

    public ClientOutput getClientByUUIDOutput(UUID clientId) {
        Clients client = clientRepository.getClientByUUID(clientId);
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
        return clientRepository.getClientByUUID(clientId);
    }
    
   public List<ClientOutput> getClients() {
        List<ClientOutput> clientOutputs = new ArrayList<>();

        for (Clients client : clientRepository.getAllClients()) {
            ClientOutput clientOutput = getClientByUUIDOutput(client.getClientId());
            if (clientOutput != null) {
                clientOutputs.add(clientOutput);
            }
        }
        return clientOutputs;
    }
    
    public void createClient(String nom, String prenom, String genre) throws Exception {
        genre = ( genre != null && genre.isEmpty()) ? "Inconnu" : genre;
        Clients client = new Clients(nom, prenom, genre);
        clientRepository.saveClient(client);    
    }

    public void deleteClient(UUID clientId) throws Exception {
        if ((clientId == null) || !clientRepository.clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        clientRepository.deleteClient(clientId);
    }

    public void patchClient(UUID clientId, Map<String, String> patchData) throws Exception {
        if (!clientRepository.clientExists(clientId)) {
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
        clientRepository.saveClient(client);
    }
    
    public Contrat getContratByUUID(UUID contratId) {
        return contratRepository.getContratByUUID(contratId);
    }

    public ContratOutput getContratByUUIDOutput(UUID contratId) {
        Contrat contrat = getContratByUUID(contratId);
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
                compteCourant.getClientId()
            );
        } else if (contrat instanceof CompteEpargne) {
            CompteEpargne compteEpargne = (CompteEpargne) contrat;
            return new CompteEpargneOutput(
                compteEpargne.getContratId(),
                compteEpargne.getType(),
                compteEpargne.getDate(),
                compteEpargne.getInteret(),
                compteEpargne.getBalance(),
                compteEpargne.getClientId(),
                compteEpargne.isLock()
            );
        }
    
        return null;
    }
    
    public List<ContratOutput> getAllContrats() {
        List<ContratOutput> contratsOutput = new ArrayList<>();
        for (Contrat contrat : contratRepository.getAllContrats()) {
            ContratOutput contratOutput = getContratByUUIDOutput(contrat.getContratId());
            if (contratOutput != null) {
                contratsOutput.add(contratOutput);
            }
        }
        return contratsOutput;
    }

    public void createContrat(UUID clientId, String type, Double balance) throws Exception {
        if (!contratRepository.contratExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        Clients client = getClientByUUID(clientId);
        Contrat contrat = ContratFactory.creerContrat(type, clientId, balance);
        contratRepository.saveContrat(contrat);
        client.addContrat(contrat);
        clientRepository.saveClient(client);
    }

    public void deleteContrat(UUID contratId) throws Exception {
        if (!contratRepository.contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        contratRepository.deleteContrat(contratId);
    }

    public void patchContrat(UUID contratId, Map<String, String> patchData) throws Exception {
        if (!contratRepository.contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        Contrat contrat = getContratByUUID(contratId);
        if (patchData.containsKey("balance")) {
            contrat.updateBalance(Double.parseDouble(patchData.get("balance")));
        }
    }


}
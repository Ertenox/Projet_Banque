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

    public ClientOutput getClientByUUIDOutput(UUID clientId) throws Exception {
        if (!clientRepository.clientExists(clientId)) {
            throw new Exception("CLient does not exist");
        }
        Clients client = clientRepository.getClientByUUID(clientId);
        ClientOutput clientOutput = new ClientOutput();
        clientOutput.setClientId(client.getClientId());
        clientOutput.setNom(client.getNom());
        clientOutput.setPrenom(client.getPrenom());
        clientOutput.setGenre(client.getGenre());
    
        List<ContratEssential> contratEssentials = new ArrayList<>();
        if (client.getContrats() != null) {
            for (UUID contratID : client.getContrats()) {
                Contrat contrat = getContratByUUID(contratID);
                ContratEssential contratEssential = new ContratEssential();
                contratEssential.setId(contrat.getContratId());
                contratEssential.setType(contrat.getType());
                contratEssentials.add(contratEssential);
            }
        }
    
        clientOutput.setContrats(contratEssentials);
        return clientOutput;
    }

    public Clients getClientByUUID(UUID clientId) throws Exception {
        if (!clientRepository.clientExists(clientId)) {
            throw new Exception("CLient does not exist");
        }
        return clientRepository.getClientByUUID(clientId);
    }
    
   public List<ClientOutput> getClients() throws Exception {
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
        if (nom == null || prenom == null || nom.isEmpty() || prenom.isEmpty())
            throw new Exception("Nom et prénom sont obligatoires");
        genre = ( genre == null || genre.isEmpty()) ? "Inconnu" : genre;
        Clients client = new Clients(nom, prenom, genre);
        clientRepository.saveClient(client);    
    }

    public void deleteClient(UUID clientId) throws Exception {
        if ((clientId == null) || !clientRepository.clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        Clients client = clientRepository.getClientByUUID(clientId);
        for (UUID contrat : client.getContrats()){
            contratRepository.deleteContrat(contrat);
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
    
    public Contrat getContratByUUID(UUID contratId) throws Exception {
        if (!contratRepository.contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        return contratRepository.getContratByUUID(contratId);
    }

    public ContratOutput getContratByUUIDOutput(UUID contratId) throws Exception {
        if (!contratRepository.contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        Contrat contrat = getContratByUUID(contratId);
    
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
        } else {
            throw new Exception("Contrat type not supported");
        }
    }
    
    public List<ContratOutput> getAllContrats() throws Exception {
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
        if (!clientRepository.clientExists(clientId)) {
            throw new Exception("Client does not exist");
        }
        Clients client = getClientByUUID(clientId);
        Contrat contrat = ContratFactory.creerContrat(type, clientId, balance);
        contratRepository.saveContrat(contrat);
        client.addContrat(contrat.getContratId());
        clientRepository.saveClient(client);
    }

    public void deleteContrat(UUID contratId) throws Exception {
        if (!contratRepository.contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        Clients client = clientRepository.getClientByUUID(getContratByUUID(contratId).getClientId());
        client.removeContrat(contratId);
        contratRepository.deleteContrat(contratId);
        clientRepository.saveClient(client);

    }

    public void patchContrat(UUID contratId, Map<String, String> patchData) throws Exception {
        if (!contratRepository.contratExists(contratId)) {
            throw new Exception("Contrat does not exist");
        }
        Contrat contrat = getContratByUUID(contratId);
        if (patchData.containsKey("balance")) {
            contrat.updateBalance(Double.parseDouble(patchData.get("balance")));
        }
        contratRepository.saveContrat(contrat);
    }

    public void applyInterest(){
        for (Contrat contrat : contratRepository.getAllContrats()) {
            if (contrat instanceof CompteEpargne){
                ((CompteEpargne)contrat).applyInterset();
                contratRepository.saveContrat(contrat);
            }
        }
    }
}
package com.imt.projet.Banque.interfaces.database;

import com.imt.projet.Banque.domain.Contrats.Contrat;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface ContratRepository {

    boolean contratExists(UUID contratId);
    List<Contrat> getAllContrats(); 
    Contrat getContratByUUID(UUID contratId);
    void saveContrat(Contrat contrat);
    void deleteContrat(UUID contratId);
}

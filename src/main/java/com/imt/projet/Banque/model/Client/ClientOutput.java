package com.imt.projet.Banque.model.Client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

import com.imt.projet.Banque.model.Contrat.ContratEssential;

@Getter
@Setter
@ToString
public class ClientOutput {

    private UUID clientId;
    private String nom;
    private String prenom;
    private String genre;
    private List<ContratEssential> contrats; 
}

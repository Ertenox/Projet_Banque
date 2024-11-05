package com.imt.projet.Banque.domain;
import java.util.List;
import java.util.UUID;

import com.imt.projet.Banque.domain.Contrats.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Clients {

    @Getter
    private UUID id;
    
    @Getter @Setter
    private String nom;

    @Getter @Setter
    private String prenom;

    @Getter @Setter
    private String genre ;

    @Getter
    private List<Contrat> contrats;

    Clients(String nom, String prenom, String genre) {
        this.id = UUID.randomUUID();
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
    }

    public void addContrat(Contrat contrat) {
        contrats.add(contrat);
    }

    public void removeContrat(Contrat contrat) {
        contrats.remove(contrat);
    }

}

package com.imt.projet.Banque.domain.model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString @Getter @Setter
public class Clients {
    private UUID ClientId;
    private String nom;
    private String prenom;
    private String genre ;
    private List<UUID> contrats;

    public Clients(String nom, String prenom, String genre) {
        this.ClientId = UUID.randomUUID();
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.contrats = new ArrayList<>();
    }

    public void addContrat(UUID contratId) {
        contrats.add(contratId);
    }

    public void removeContrat(UUID contratId) {
        contrats.remove(contratId);
    }

}

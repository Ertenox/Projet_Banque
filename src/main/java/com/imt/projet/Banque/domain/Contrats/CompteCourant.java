package com.imt.projet.Banque.domain.Contrats;
import lombok.Getter;
import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;

@Getter
public class CompteCourant implements Contrat {
    private UUID id;
    private String type;
    private Date date;
    private Clients client;
    private Double interet;
    private Double balance;

    public CompteCourant(Clients client, Double balance) {
        this.id = UUID.randomUUID();
        this.type = "compte courant";
        this.date = new Date();
        this.client = client;
        this.interet = 0.0; 
        this.balance = balance;
    }

    public void increaseBalance(Double montant) {
        this.balance += montant;
    }

    public void decreaseBalance(Double montant) {
        this.balance -= montant;
    }

}

package com.imt.projet.Banque.domain.Contrats;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;

@Getter @ToString
public class CompteCourant implements Contrat {
    private UUID contratId;
    private String type;
    private Date date;
    private Clients client;
    private Double balance;

    public CompteCourant(Clients client, Double balance) {
        this.contratId = UUID.randomUUID();
        this.type = "compte courant";
        this.date = new Date();
        this.client = client;
        this.balance = balance;
    }

    public void updateBalance(Double montant) {
        this.balance += montant;
    }

}

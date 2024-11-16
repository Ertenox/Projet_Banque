package com.imt.projet.Banque.domain.Contrats;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;

@Getter @ToString
public class CompteEpargne implements Contrat {
    private Clients client;
    private UUID contratId;
    private String type;
    private Date date;
    private static final Double INTERET = 0.03;
    private boolean status;
    private Double balance;

    public CompteEpargne(Clients client, Double balance) {
        this.contratId = UUID.randomUUID();
        this.type = "compte épargne";
        this.date = new Date();
        this.client = client;
        this.status = true;
        this.balance = balance;
    }

    public void unLock() {
        this.status = false;
    }

    public void updateBalance(Double montant) {
        this.balance += montant;
    }

    public Double getInteret() {
        return INTERET;
    }
}

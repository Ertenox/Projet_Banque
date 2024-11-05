package com.imt.projet.Banque.domain.Contrats;
import lombok.Getter;
import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;

@Getter
public class CompteEpargne implements Contrat {
    private UUID id;
    private String type;
    private Date date;
    private Clients client;
    private Double interet;
    private boolean isBlocked;
    private Double balance;

    public CompteEpargne(Clients client) {
        this.id = UUID.randomUUID();
        this.type = "compte épargne";
        this.date = new Date();
        this.client = client;
        this.isBlocked = true;
    }

    public void unLock() {
        this.isBlocked = false;
    }

    public void increaseBalance(Double montant) {
        this.balance += montant;
    }

    public void decreaseBalance(Double montant) {
        this.balance -= montant;
    }
}

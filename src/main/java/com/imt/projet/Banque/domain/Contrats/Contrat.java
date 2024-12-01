package com.imt.projet.Banque.domain.Contrats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;


@Getter @ToString @AllArgsConstructor
public abstract class Contrat {
    private UUID contratId;
    private String type;
    private Date date;
    private UUID clientId;
    @Setter
    private Double balance;

    public Contrat(UUID clientId, Double balance, String type) {
        this.contratId = UUID.randomUUID();
        this.type = type;
        this.date = new Date();
        this.clientId = clientId;
        this.balance = balance;
    }

    public void updateBalance(Double montant) {
        this.balance += montant;
    }
}

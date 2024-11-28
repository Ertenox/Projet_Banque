package com.imt.projet.Banque.domain.model.Contrat;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ContratOutput {
    private UUID contratId;
    private UUID clientId;   
    private String type;
    private Date date;
    private Double balance;
    

    public ContratOutput(UUID contratId, String type, Date date, Double balance, UUID clientId) {
        this.contratId = contratId;
        this.type = type;
        this.date = date;
        this.balance = balance;
        this.clientId = clientId;
    }
}

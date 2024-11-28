package com.imt.projet.Banque.domain.model.Contrat.Type;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.model.Contrat.ContratOutput;

@Getter
@Setter
public class CompteEpargneOutput extends ContratOutput {
    private Boolean status;
    private Double interet;

    public CompteEpargneOutput(UUID contratId, String type, Date date, Double interet, Double balance, UUID clientId, Boolean status) {
        super(contratId, type, date, balance, clientId);
        this.interet = interet;
        this.status = status;
    }
}

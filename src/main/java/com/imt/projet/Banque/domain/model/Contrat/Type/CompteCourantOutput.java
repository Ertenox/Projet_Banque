package com.imt.projet.Banque.domain.model.Contrat.Type;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.model.Contrat.ContratOutput;

@Getter
@Setter
public class CompteCourantOutput extends ContratOutput {

    public CompteCourantOutput(UUID contratId, String type, Date date, Double balance, UUID clientId) {
        super(contratId, type, date, balance, clientId);
    }
}

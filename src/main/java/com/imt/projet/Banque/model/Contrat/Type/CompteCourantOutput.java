package com.imt.projet.Banque.model.Contrat.Type;

import com.imt.projet.Banque.model.Contrat.ContratOutput;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CompteCourantOutput extends ContratOutput {

    public CompteCourantOutput(UUID contratId, String type, Date date, Double balance, UUID clientId) {
        super(contratId, type, date, balance, clientId);
    }
}

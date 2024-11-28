package com.imt.projet.Banque.domain.Contrats;

import java.util.Date;
import java.util.UUID;


public class CompteCourant extends Contrat {
    public CompteCourant(UUID clientId, Double balance) {
        super(clientId, balance, "compte courant");
    }

    public CompteCourant(UUID contratId, UUID clientId, Double balance, Date date) {
        super(contratId, "compte courant", date, clientId, balance);
    }
    
}

package com.imt.projet.Banque.domain.Contrats;

import java.util.Date;
import java.util.UUID;


import lombok.Getter;


public class CompteEpargne extends Contrat {
    private static final Double INTERET = 0.03;

    @Getter
    private boolean lock;

    public CompteEpargne(UUID clientId, Double balance) {
        super(clientId, balance, "compte épargne");
        this.lock = true;
    }

    public CompteEpargne(UUID contratId, UUID clientId, Double balance, Date date, boolean lock) {
        super(contratId, "compte courant", date, clientId, balance);
        this.lock = lock;
    }


    public void unLock() {
        this.lock = false;
    }

    public Double getInteret() {
        return INTERET;
    }
}

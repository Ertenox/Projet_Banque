package com.imt.projet.Banque.domain.Contrats;
import java.util.Date;
import java.util.UUID;

import com.imt.projet.Banque.domain.Clients;

public interface Contrat {
    UUID getContratId();
    String getType();
    Date getDate();
    Clients getClient();
    Double getBalance();
    void updateBalance(Double montant);
    String toString();
}

package com.imt.projet.Banque.domain.Contrats;
import java.util.UUID;

public class ContratFactory {

    public static Contrat creerContrat(String type, UUID clientId, Double balance) {
        switch (type.toLowerCase()) {
            case "epargne":
                return new CompteEpargne(clientId, balance);
            default :
                return new CompteCourant(clientId, balance);
        }

    }
}


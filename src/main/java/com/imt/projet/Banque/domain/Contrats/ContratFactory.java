package com.imt.projet.Banque.domain.Contrats;
import com.imt.projet.Banque.domain.Clients;

public class ContratFactory {

    public static Contrat creerContrat(String type, Clients client, Double balance) {
        switch (type.toLowerCase()) {
            case "epargne":
                return new CompteEpargne(client, balance);
            default :
                return new CompteCourant(client, balance);
        }

    }
}


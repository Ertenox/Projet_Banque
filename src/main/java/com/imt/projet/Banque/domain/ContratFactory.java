package com.imt.projet.Banque.domain;
import com.imt.projet.Banque.domain.Contrats.*;

public class ContratFactory {

    // Méthode de fabrique pour créer un contrat en fonction du type
    public static Contrat creerContrat(String type, Clients client) {
        // En fonction du type de contrat, on peut ajouter des spécificités ici
        switch (type.toLowerCase()) {
            case "epargne":
                return new CompteEpargne(client);
            default :
                return new CompteCourant(client, 0.0);
        }

    }
}


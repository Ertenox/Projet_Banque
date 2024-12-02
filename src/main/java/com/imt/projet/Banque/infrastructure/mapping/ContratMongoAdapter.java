package com.imt.projet.Banque.infrastructure.mapping;

import com.imt.projet.Banque.domain.Contrats.CompteCourant;
import com.imt.projet.Banque.domain.Contrats.CompteEpargne;
import com.imt.projet.Banque.domain.Contrats.Contrat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.imt.projet.Banque.infrastructure.model.ContratMongo;


@Component
public class ContratMongoAdapter {
    public Contrat toDomain(ContratMongo contratMongo) {
        if (contratMongo == null) {
            return null;
        }
        if  ("compte courant".equals(contratMongo.getType())) {
            return new CompteCourant(
                    contratMongo.getContratId(),
                    contratMongo.getClientId(),
                    contratMongo.getBalance(),
                    contratMongo.getDate()
                );
        } else if ("compte epargne".equals(contratMongo.getType())) {
            return new CompteEpargne(
                    contratMongo.getContratId(),
                    contratMongo.getClientId(),
                    contratMongo.getBalance(),
                    contratMongo.getDate(),
                    contratMongo.isLock()
            );

        } else {
            throw new IllegalArgumentException("Type de contrat inconnu : " + contratMongo.getType());
        }
    }

    public List<Contrat> toDomain(List<ContratMongo> contratsMongo) {
        if (contratsMongo == null) {
            return null;
        }
        List<Contrat> contratsDomain = new ArrayList<>();
        for (ContratMongo contratMongo : contratsMongo) {
            contratsDomain.add(toDomain(contratMongo));
        }

        return contratsDomain;
    }

    public ContratMongo toMongo(Contrat contrat) {
        if (contrat == null) {
            return null;
        }

        if (contrat instanceof CompteEpargne) {
            CompteEpargne compteEpargne = (CompteEpargne) contrat;
            return new ContratMongo(
                    compteEpargne.getContratId(),
                    compteEpargne.getType(),
                    compteEpargne.getDate(),
                    compteEpargne.getClientId(),
                    compteEpargne.getBalance(),
                    compteEpargne.isLock(),
                    compteEpargne.getInteret()
            );
        } else if (contrat instanceof CompteCourant) {
            return new ContratMongo(
                    contrat.getContratId(),
                    contrat.getType(),
                    contrat.getDate(),
                    contrat.getClientId(),
                    contrat.getBalance(),
                    false, // Non applicable pour CompteCourant
                    null   // Non applicable pour CompteCourant
            );
        } else {
            throw new IllegalArgumentException("Type de contrat non pris en charge : " + contrat.getClass());
        }
    }

    public List<ContratMongo> toMongo(List<Contrat> contrats) {
        if (contrats == null) {
            return null;
        }

        List<ContratMongo> contratsMongo = new ArrayList<>();
        for (Contrat contrat : contrats) {
            contratsMongo.add(toMongo(contrat));
        }

        return contratsMongo;
    }
}

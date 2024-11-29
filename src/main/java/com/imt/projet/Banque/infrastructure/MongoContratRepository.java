package com.imt.projet.Banque.infrastructure;

import com.imt.projet.Banque.domain.Contrats.Contrat;
import com.imt.projet.Banque.interfaces.database.ContratRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.imt.projet.Banque.infrastructure.mapping.ContratMongoAdapter;
import com.imt.projet.Banque.infrastructure.model.ContratMongo;

import java.util.List;
import java.util.UUID;

@Repository
public class MongoContratRepository implements ContratRepository {

    ContratMongoAdapter contratAdapter = new ContratMongoAdapter();

    @Autowired
    private MongoContratSpringRepository mongoContratSpringRepository;

    @Override
    public boolean contratExists(UUID contratId) {
        return mongoContratSpringRepository.existsById(contratId);
    }

    @Override
    public Contrat getContratByUUID(UUID contratId) {
        return contratAdapter.toDomain(mongoContratSpringRepository.findById(contratId).orElse(null));
    }

    @Override
    public List<Contrat> getAllContrats() {
        return contratAdapter.toDomain(mongoContratSpringRepository.findAll());  
    }

    @Override
    public void saveContrat(Contrat contrat) {
        mongoContratSpringRepository.save(contratAdapter.toMongo(contrat));
    }

    @Override
    public void deleteContrat(UUID contratId) {
        mongoContratSpringRepository.deleteById(contratId);
    }
}

@Repository
interface MongoContratSpringRepository extends MongoRepository<ContratMongo, UUID> {
}


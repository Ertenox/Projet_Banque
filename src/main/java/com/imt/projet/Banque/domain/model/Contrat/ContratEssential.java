package com.imt.projet.Banque.domain.model.Contrat;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ContratEssential {
    private UUID id;
    private String type;  
}

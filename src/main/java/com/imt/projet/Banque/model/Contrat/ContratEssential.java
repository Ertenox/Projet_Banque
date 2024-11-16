package com.imt.projet.Banque.model.Contrat;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ContratEssential {
    private UUID id;
    private String type;  
}

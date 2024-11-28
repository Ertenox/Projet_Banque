package com.imt.projet.Banque.domain.model.Contrat;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContratInput {

    @NotEmpty(message = "Le type de contrat est obligatoire.")

    private String type; 

    @NotEmpty(message = "L'ID du client est obligatoire.")
    private String clientId; 

    @NotEmpty(message = "Le solde initial est obligatoire.")
    @DecimalMin(value = "10", message = "Le solde initial doit être supérieur à 10.")
    private Double balanceInitiale; 
    
}

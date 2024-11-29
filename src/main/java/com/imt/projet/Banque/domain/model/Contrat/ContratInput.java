package com.imt.projet.Banque.domain.model.Contrat;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContratInput {

    @NotBlank(message = "Le type de contrat est obligatoire.")
    private String type; 

    @NotBlank(message = "L'ID du client est obligatoire.")
    private String clientId; 

    @NotNull(message = "Le montant initial est obligatoire")
    @DecimalMin(value = "10", message = "Le solde initial doit être supérieur à 10.")
    private Double balanceInitiale; 
    
}

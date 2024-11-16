package com.imt.projet.Banque.model.Contrat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContratInput {

    @NotBlank(message = "Le type de contrat est obligatoire.")
    private String type; 

    @NotNull(message = "L'ID du client est obligatoire.")
    private String clientId; 

    @NotNull(message = "Le solde initial est obligatoire.")
    @DecimalMin(value = "10", message = "Le solde initial doit être supérieur à 10.")
    private Double balanceInitiale; 
}

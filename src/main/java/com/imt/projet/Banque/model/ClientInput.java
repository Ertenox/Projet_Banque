package com.imt.projet.Banque.model;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter @Setter @NoArgsConstructor
public class ClientInput {

    @NotBlank(message = "Le prénom est obligatoire.")
    @Size(min = 3, max = 50, message = "Le prénom ne peut pas dépasser 50 caractères.")
    private String prenom;

    @NotBlank(message = "Le nom est obligatoire.")
    @Size(min = 3, max = 50, message = "Le nom ne peut pas dépasser 50 caractères.")
    private String nom;

    private String genre;

}

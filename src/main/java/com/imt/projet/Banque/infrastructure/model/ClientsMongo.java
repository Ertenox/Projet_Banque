
package com.imt.projet.Banque.infrastructure.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Document(collection = "Clients")
@AllArgsConstructor @Getter @Setter
public class ClientsMongo {

    @Id
    private UUID id;  

    private String nom;
    private String prenom;
    private String genre;
    private List<ContratMongo> contrats; 

}
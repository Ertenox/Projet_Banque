package com.imt.projet.Banque.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
@Document(collection = "Contrats")
public class ContratMongo {
    @Id
    private UUID contratId;

    private String type;
    private Date date;
    private UUID clientId;
    private Double balance;
    private boolean lock; 
    private Double interet; 

    public ContratMongo(UUID contratId, String type, Date date, UUID clientId, Double balance) {
        this.contratId = contratId;
        this.type = type;
        this.date = date;
        this.clientId = clientId;
        this.balance = balance;
        this.lock = false;
        this.interet = null; // Par défaut, champs non utilisés pour les comptes courants
    }
}

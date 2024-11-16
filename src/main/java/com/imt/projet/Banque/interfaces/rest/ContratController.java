package com.imt.projet.Banque.interfaces.rest;

import com.imt.projet.Banque.application.Metier;
import com.imt.projet.Banque.domain.Contrats.*;
import com.imt.projet.Banque.model.Client.ClientOutput;
import com.imt.projet.Banque.model.Contrat.ContratInput;
import com.imt.projet.Banque.model.Contrat.ContratOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/banque/contrat")
public class ContratController {

    private final Metier metier;

    @Autowired
    public ContratController(Metier metier) {
        this.metier = metier;
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createContrat(@Valid @RequestBody ContratInput contratInput) {
        try {
            metier.createContrat(
                UUID.fromString(contratInput.getClientId()),
                contratInput.getType(),
                contratInput.getBalanceInitiale()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Contrat créé avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du contrat.");
        }
    }
    
    @GetMapping(path = "/byId", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ContratOutput getContratById(@RequestParam String contratId) {
        return metier.getContratByUUIDOutput(UUID.fromString(contratId));
    }

    @GetMapping(path = "/all", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ContratOutput> getAllContrats() {
        List<ContratOutput> ContratOutput = metier.getAllContrats(); 
        return ContratOutput; 
    }
    
    @DeleteMapping(path = "/delete", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContrat(@RequestParam String contratId) throws Exception {
        metier.deleteContrat(UUID.fromString(contratId));
    }

    @PatchMapping(path = "/patch", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void patchContrat(@RequestBody Map<String, String> patchRequest) throws Exception {
    String contratIdStr = (String) patchRequest.get("contratId");
    if (contratIdStr == null) {
        throw new Exception("contratId est requis");
    }
    UUID contratId = UUID.fromString(contratIdStr);

    Map<String, String> patchData = new HashMap<>();
    if (patchRequest.containsKey("balance")) {
        patchData.put("balance", patchRequest.get("balance").toString());
    }
        metier.patchContrat(contratId, patchData);
    }

}

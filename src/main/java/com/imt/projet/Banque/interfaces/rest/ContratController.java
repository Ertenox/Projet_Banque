package com.imt.projet.Banque.interfaces.rest;

import com.imt.projet.Banque.application.Metier;
import com.imt.projet.Banque.domain.model.Contrat.ContratInput;
import com.imt.projet.Banque.domain.model.Contrat.ContratOutput;
import com.imt.projet.Banque.interfaces.rest.exceptions.ErrorManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.Validator;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@RestController
@RequestMapping("/banque/contrat")
public class ContratController {

    private final Metier metier;
    ErrorManager ErrManager = new ErrorManager();

    @Autowired
    public ContratController(Metier metier) {
        this.metier = metier;
    }

    @Autowired
    private Validator  validator;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createContrat(@RequestBody ContratInput contratInput) throws Exception {

        BindingResult result = new BeanPropertyBindingResult(contratInput, "contratInput");
        validator.validate(contratInput, result);


        if (result.hasErrors()) {
        StringBuilder errorMessage = new StringBuilder("Erreur de validation : ");
        for (ObjectError error : result.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append(", ");
        }
        
        // Enlever la dernière virgule et espace
        if (errorMessage.length() > 0) {
            errorMessage.setLength(errorMessage.length() - 2);
        }
        
        return ResponseEntity.badRequest().body(errorMessage.toString());
    }
        try {
            metier.createContrat(
                UUID.fromString(contratInput.getClientId()),
                contratInput.getType(),
                contratInput.getBalanceInitiale()

            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Contrat créé avec succès.");
        } catch (Exception e) {
            return ErrManager.handleException(e);
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
    public void patchContrat(@RequestBody Map<String, String> patchRequest)  {
    String contratIdStr = (String) patchRequest.get("contratId");
    try {
        if (contratIdStr == null) {
            throw new IllegalArgumentException("contratId est requis");
        }
        UUID contratId = UUID.fromString(contratIdStr);

        Map<String, String> patchData = new HashMap<>();
        if (patchRequest.containsKey("balance")) {
            patchData.put("balance", patchRequest.get("balance").toString());
        }
            metier.patchContrat(contratId, patchData);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

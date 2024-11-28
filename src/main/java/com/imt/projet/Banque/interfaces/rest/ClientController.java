package com.imt.projet.Banque.interfaces.rest;


import com.imt.projet.Banque.application.Metier;
import com.imt.projet.Banque.domain.model.Client.ClientInput;
import com.imt.projet.Banque.domain.model.Client.ClientOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/banque/client")
public class ClientController {

    private final Metier metier;

    @Autowired
    public ClientController(Metier metier) {
        this.metier = metier;
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createClient(@Valid @RequestBody ClientInput clientInput) throws Exception {
        if (clientInput.getNom().isEmpty() || clientInput.getPrenom().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Le nom et le prénom ne doivent pas être vides.");
        }
        metier.createClient(clientInput.getNom(), clientInput.getPrenom(), clientInput.getGenre());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/byId", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ClientOutput getClientById(@RequestParam String clientId) {
        return metier.getClientByUUIDOutput(UUID.fromString(clientId));
    }

    @GetMapping(path = "/all", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientOutput> getAllClients() {
        List<ClientOutput> clientOutputs = metier.getClients(); 
        return clientOutputs; 
    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@RequestParam String clientId) throws Exception {
        metier.deleteClient(UUID.fromString(clientId));
    }

    @PatchMapping(path = "/patch", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void patchClient(@RequestParam String clientId, @RequestBody Map<String, String> patchData) throws Exception {
        metier.patchClient(UUID.fromString(clientId), patchData);
    }
}



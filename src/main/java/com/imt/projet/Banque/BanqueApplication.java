package com.imt.projet.Banque;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.imt.projet.Banque.application.Metier;
import com.imt.projet.Banque.domain.Clients;
import com.imt.projet.Banque.model.ClientInput;

import jakarta.validation.Valid;

@SpringBootApplication
public class BanqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
	}
}

@RestController
@RequestMapping("/banque")
class BanqueApplicationController {

	private Metier metier = new Metier();

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!";
	}

    @PostMapping(path = "/client/createClient", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createClient(@Valid @RequestBody ClientInput clientInput) throws Exception {
        if (clientInput.getNom().isEmpty() || clientInput.getPrenom().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .body("Le nom et le prénom ne doivent pas être vides."); 
        }
        metier.createClient(clientInput.getNom(), clientInput.getPrenom(), clientInput.getGenre());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


	@GetMapping(path = "/client/byId", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Clients getClientById(@RequestParam String id) {
		return metier.getClientsByUUID(UUID.fromString(id));
	}

	@GetMapping(path = "/client/all", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public String getAllClients() {
		return metier.getClients();
	}



}

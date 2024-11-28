package com.imt.projet.Banque.interfaces.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ErrorManager {

    public ResponseEntity<String> handleException(Exception e) {
        if (e instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) e;
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        } else if (e instanceof MethodArgumentNotValidException) {
            // Gestion des erreurs de validation
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = manve.getBindingResult().getAllErrors();
            StringBuilder errorMessage = new StringBuilder("Erreur de validation : ");

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(", ");
            }

            // Retirer la dernière virgule et espace
            if (errorMessage.length() > 0) {
                errorMessage.setLength(errorMessage.length() - 2);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

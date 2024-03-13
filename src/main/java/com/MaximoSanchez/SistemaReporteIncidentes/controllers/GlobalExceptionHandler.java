package com.MaximoSanchez.SistemaReporteIncidentes.controllers;

import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.DuplicatedResourceException;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.InvalidRequestParameterException;
import com.MaximoSanchez.SistemaReporteIncidentes.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<String>("The resource: " + ex.getMessage() + " was not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedResourceException.class)
    public ResponseEntity<String> handleDuplicatedResource(DuplicatedResourceException ex) {
        return new ResponseEntity<String>("The resource: " + ex.getMessage() + " already exists.", HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidRequestParameterException.class)
    public ResponseEntity<String> handleDuplicatedResource(InvalidRequestParameterException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

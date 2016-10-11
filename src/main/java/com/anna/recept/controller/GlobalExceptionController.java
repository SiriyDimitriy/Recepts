package com.anna.recept.controller;

import com.anna.recept.exception.ReceptApplicationException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ReceptApplicationException.class)
    public ResponseEntity<JsonNode> handleCustomException(Exception ex) {
        JsonNode jn = JsonNodeFactory.instance.textNode(ex.getMessage());
        return new ResponseEntity<>(jn, HttpStatus.BAD_REQUEST);
    }
}

package edu.unc.clinica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Maneja la excepción EntityNotFoundException.
     *
     * @param ex       La excepción EntityNotFoundException lanzada.
     * @param request  La solicitud web en la que se produjo la excepción.
     * @return         Una respuesta de entidad que contiene un mensaje de error y el estado HTTP correspondiente.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
   
    /**
     * Maneja la excepción IllegalOperationException.
     *
     * @param ex       La excepción IllegalOperationException lanzada.
     * @param request  La solicitud web en la que se produjo la excepción.
     * @return         Una respuesta de entidad que contiene un mensaje de error y el estado HTTP correspondiente.
     */
    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorMessage> illegalOperationException(IllegalOperationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
   
    /**
     * Maneja excepciones generales.
     *
     * @param ex       La excepción general lanzada.
     * @param request  La solicitud web en la que se produjo la excepción.
     * @return         Una respuesta de entidad que contiene un mensaje de error genérico y el estado HTTP correspondiente.
     */
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

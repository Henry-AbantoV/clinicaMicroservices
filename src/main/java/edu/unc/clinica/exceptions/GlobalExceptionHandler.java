package edu.unc.clinica.exceptions;


import java.util.HashMap;
import java.util.Map;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import edu.unc.clinica.util.ApiResponse;


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
   
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errores.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        ApiResponse<Object> response = new ApiResponse<>(false, "Error de validación", errores);
        return ResponseEntity.badRequest().body(response);
    }

}

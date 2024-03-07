/*
 * @file ErrorMessage.java;
 * @Autor Henry AV (c)2024
 * @Created 5 mar 2024,12:24:38
 */
package edu.unc.clinica.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorMessage.
 */
@Getter
@Setter
public class ErrorMessage {
	
	/** The Constant FACTURA_NOT_FOUND. */
	public static final String FACTURA_NOT_FOUND = "La factura con id proporcionado no fue encontrada";
	
	/** The Constant CITA_NOT_FOUND. */
	public static final String CITA_NOT_FOUND = "La cita con id proporcionado no fue encontrada";
	
	/** The Constant MEDICO_NOT_FOUND. */
	public static final String MEDICO_NOT_FOUND = "El medico con id proporcionado no fue encontrado";
	
	/** The Constant ESPECIALIDAD_NOT_FOUND. */
	public static final String ESPECIALIDAD_NOT_FOUND = "La especialidad con id proporcionado no fue encontrada";
	
	/** The Constant PACIENTE_NOT_FOUND. */
	public static final String PACIENTE_NOT_FOUND = "El paciente con id proporcionado no fue encontrado";
	
	/** The Constant HISTORIAL_MEDICO_NOT_FOUND. */
	public static final String HISTORIAL_MEDICO_NOT_FOUND = "El historial medico con id proporcionado no fue encontrado";
	
	
    /** The status code. */
    // El código de estado HTTP asociado con el error.
    private int statusCode;

    /** The timestamp. */
    // La marca de tiempo en la que se produjo el error.
    private LocalDateTime timestamp;

    /** The message. */
    // El mensaje descriptivo del error.
    private String message;

    /** The description. */
    // La descripción detallada del error.
    private String description;

    /**
     * Constructor que crea una nueva instancia de ErrorMessage.
     *
     * @param statusCode   El código de estado HTTP asociado con el error.
     * @param message      El mensaje descriptivo del error.
     * @param description  La descripción detallada del error.
     */
    public ErrorMessage(HttpStatus statusCode, String message, String description) {
        // Establece el código de estado, la marca de tiempo, el mensaje y la descripción del error.
        this.statusCode = statusCode.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.description = description;
    }
}

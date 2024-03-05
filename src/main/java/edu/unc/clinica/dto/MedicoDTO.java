/*
 * @file MedicoDTO.java;
 * @Autor YerssonC.D (c)2024
 * @Created 5 mar 2024,0:29:16
 */
package edu.unc.clinica.dto;

import edu.unc.clinica.domain.Especialidad;
import lombok.Data;
/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para un médico.
 * Contiene información sobre el médico, incluyendo su ID, nombre, apellidos, teléfono,
 * horario de trabajo, correo electrónico y la especialidad a la que pertenece.
 */
@Data
public class MedicoDTO {

    /**
     * El ID del médico.
     */
	private Long idMedico;
	

    /**
     * El nombre del médico.
     */
	private String nombre;
	
	/**
     * Los apellidos del médico.
     */
	private String apellidos;

	  /**
     * El número de teléfono del médico.
     */
	private String telefono;
	
	/**
     * El horario de trabajo del médico.
     */
	private String horario;
	
	/**
     * La dirección de correo electrónico del médico.
     */
	private String correoElectronico;
	
	 /**
     * La especialidad a la que pertenece el médico.
     */
	private Especialidad especialidad;
}

/*
 * @file MedicoDTO.java;
 * @Autor YerssonC.D (c)2024
 * @Created 5 mar 2024,0:29:16
 */
package edu.unc.clinica.dto;

import edu.unc.clinica.domain.Especialidad;
import lombok.Data;

@Data
public class MedicoDTO {

	private Long idMedico;
	
	private String nombre;
	
	private String apellidos;

	private String telefono;
	
	private String horario;
	
	private String correoElectronico;
	
	private Especialidad especialidad;
}

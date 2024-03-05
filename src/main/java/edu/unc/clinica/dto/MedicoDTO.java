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

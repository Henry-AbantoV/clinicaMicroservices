package edu.unc.clinica.dto;

import java.util.Date;

import edu.unc.clinica.domain.Factura;
import lombok.Data;

//Declaración de la clase CitaDTO
@Data
public class CitaDTO {
	 // Campo para almacenar el identificador único de la cita
	private Long idCita;
	// Campo para almacenar la fecha y hora de la cita
    private Date fechaHoraCita;
    
    // Campo para almacenar el motivo o razón de la cita
    private String motivoCita;
    
    // Campo para almacenar el estado actual de la cita
    private String estadoCita;
    
    // Campo para almacenar la factura asociada a la cita
	private Factura factura;
}

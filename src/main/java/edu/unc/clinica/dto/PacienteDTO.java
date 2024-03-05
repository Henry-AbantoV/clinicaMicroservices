package edu.unc.clinica.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.HistorialMedico;
import lombok.Data;

/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para un paciente.
 * Contiene información sobre el paciente, incluyendo su ID, nombres, apellidos, fecha de nacimiento,
 * género, número de documento de identidad (DNI), dirección, número de teléfono, dirección de correo electrónico,
 * lista de citas asociadas y el historial médico del paciente.
 */
@Data
public class PacienteDTO {
	/**
     * El ID del paciente.
     */
	private Long idPaciente;
	  /**
     * Los nombres del paciente.
     */
    private String nombres;
    
    /**
     * Los apellidos del paciente.
     */
    private String apellidos;
    
    /**
     * La fecha de nacimiento del paciente.
     */
    private Date fechaNacimiento;
    
    /**
     * El género del paciente.
     */
    private String genero;
    
    /**
     * El número de documento de identidad (DNI) del paciente.
     */
    private String dni;
    
    /**
     * La dirección del paciente.
     */
    private String direccion;
    
    /**
     * El número de teléfono del paciente.
     */
    private String telefono;
    
    /**
     * La dirección de correo electrónico del paciente.
     */
    private String correoElectronico;
   
    /**
     * Lista de citas asociadas al paciente.
     */
    private List<Cita> citas = new ArrayList<>();
    
    /**
     * El historial médico del paciente.
     */
    private HistorialMedico historialMedico;
}

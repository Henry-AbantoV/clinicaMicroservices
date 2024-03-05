
package edu.unc.clinica.dto;

import java.util.Date;

import edu.unc.clinica.domain.Paciente;
import lombok.Data;

/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para un historial médico.
 * Contiene información sobre el historial médico, incluyendo su ID, fecha de registro, diagnósticos anteriores,
 * tratamientos previos, procedimientos realizados, medicamentos recetados, resultados de pruebas médicas
 * y el paciente asociado.
 */
@Data
public class HistorialDTO {
	 /**
     * El ID del historial médico.
     */
	private long idHistorialMedico;
	
	/**
     * La fecha de registro del historial médico.
     */
    private Date fechaRegistro;
    
    /**
     * Los diagnósticos anteriores registrados en el historial médico.
     */
	private String diagnosticosAnteriores;
	
	/**
     * Los tratamientos previos registrados en el historial médico.
     */
    private String tratamientosPrevios;
    
    /**
     * Los procedimientos realizados registrados en el historial médico.
     */
    private String procedimientosRealizados;
    
    /**
     * Los medicamentos recetados registrados en el historial médico.
     */
    private String medicamentosRecetados;
    
    /**
     * Los resultados de las pruebas médicas registrados en el historial médico.
     */
    private String ResultadosPruebasMedicas;
    
    /**
     * El paciente asociado al historial médico.
     */
    private Paciente paciente;
    
    
}

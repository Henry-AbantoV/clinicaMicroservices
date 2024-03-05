package edu.unc.clinica.dto;
import java.util.ArrayList;
import java.util.List;

import edu.unc.clinica.domain.Medico;
import lombok.Data;

/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para una especialidad médica.
 * Contiene información sobre la especialidad médica, incluyendo su ID, nombre y lista de médicos asociados.
 */
@Data
public class EspecialidadDTO {

    /**
     * El ID de la especialidad médica.
     */
	private Long idEspecialidad;
	
    /**
     * El nombre de la especialidad médica.
     */
	private String nombreEsp;
	
	/**
     * Lista de médicos asociados a la especialidad médica.
     */	
	private List<Medico> medicos=new ArrayList<>();
	
}

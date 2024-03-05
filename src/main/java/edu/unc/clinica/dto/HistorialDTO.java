
package edu.unc.clinica.dto;

import java.util.Date;

import edu.unc.clinica.domain.Paciente;
import lombok.Data;

@Data
public class HistorialDTO {
	private long idHistorialMedico;
    private Date fechaRegistro;
	private String diagnosticosAnteriores;
    private String tratamientosPrevios;
    private String procedimientosRealizados;
    private String medicamentosRecetados;
    private String ResultadosPruebasMedicas;
    
    private Paciente paciente;
    
    
}

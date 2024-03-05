package edu.unc.clinica.dto;



import java.util.ArrayList;
import java.util.List;

import edu.unc.clinica.domain.Medico;
import lombok.Data;

@Data
public class EspecialidadDTO {

	private Long idEspecialidad;
	private String nombreEsp;
	
	private List<Medico> medicos=new ArrayList<>();
	
}

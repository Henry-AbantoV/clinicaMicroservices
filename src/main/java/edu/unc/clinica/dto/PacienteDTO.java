package edu.unc.clinica.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.HistorialMedico;
import lombok.Data;


@Data
public class PacienteDTO {

	private Long idPaciente;

    private String nombres;

    private String apellidos;

    private Date fechaNacimiento;

    private String genero;

    private String dni;

    private String direccion;

    private String telefono;
    private String correoElectronico;
    
    private List<Cita> citas = new ArrayList<>();
    
    private HistorialMedico historialMedico;
}

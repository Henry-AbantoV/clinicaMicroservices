package edu.unc.clinica.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.Paciente;
import lombok.Data;

@Data
public class FacturaDTO {

	private Long idFactura;
    private Date fechaEmision;
    private String descripServicios;
    private String pagoRealizados;
    private double saldoPendiente;
    private double costo;
    
	private List<Cita> citas=new ArrayList<>();
	private List<Paciente> pacientes=new ArrayList<>();
}

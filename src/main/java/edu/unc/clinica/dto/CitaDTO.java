package edu.unc.clinica.dto;

import java.util.Date;

import edu.unc.clinica.domain.Factura;
import lombok.Data;

@Data
public class CitaDTO {

	private Long idCita;
    private Date fechaHoraCita;
    private String motivoCita;
    private String estadoCita;

	private Factura factura;
}

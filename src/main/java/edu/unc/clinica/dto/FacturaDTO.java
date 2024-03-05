package edu.unc.clinica.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.Paciente;
import lombok.Data;

/**
 * Clase que representa un DTO (Objeto de Transferencia de Datos) para una factura.
 * Contiene información sobre la factura, incluyendo su ID, fecha de emisión, descripción de servicios,
 * pagos realizados, saldo pendiente, costo y listas de citas y pacientes asociados.
 */
@Data
public class FacturaDTO {

    /**
     * El ID de la factura.
     */
	private Long idFactura;
	/**
     * La fecha de emisión de la factura.
     */
    private Date fechaEmision;
    /**
     * La descripción de los servicios proporcionados en la factura.
     */
    private String descripServicios;

    /**
     * Los pagos realizados hasta la fecha de la factura.
     */
    private String pagoRealizados;

    /**
     * El saldo pendiente de la factura.
     */
    private double saldoPendiente;
    /**
     * El costo total de los servicios facturados.
     */
    private double costo;
    
    /**
     * Lista de citas asociadas a la factura.
     */
	private List<Cita> citas=new ArrayList<>();
	/**
     * Lista de pacientes asociados a la factura.
     */
	private List<Paciente> pacientes=new ArrayList<>();
}

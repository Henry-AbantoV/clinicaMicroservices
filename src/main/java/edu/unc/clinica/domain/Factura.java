/*
 * @file Factura.java;
 * @Autor Daniela Torres (c)2024
 * @Created 5 mar 2024,0:31:47
 */
package edu.unc.clinica.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * La clase Factura representa una factura en el sistema.
 * Esta clase está marcada con la anotación @Entity para indicar que es una entidad de base de datos.
 * También utiliza las anotaciones Lombok @Data para generar automáticamente los métodos getter, setter, toString, equals y hashCode.
 */

@Entity
@Data

public class Factura {

	/** The id factura. */
	// Identificador único de la factura.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    /** The fecha emision. */
    // Fecha de emisión de la factura.
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "La fecha debe ser anterior al dia de hoy")
    @Past(message = "La fecha de inscripción debe ser en el pasado")
    private Date fechaEmision;

    /** The descrip servicios. */
    // Descripción de los servicios incluidos en la factura.
    @NotBlank(message = "Descripcion de la factura no puede estar vacío.")
    private String descripServicios;

    /** The pago realizados. */
    // Detalles de los pagos realizados.
    @NotBlank(message = "El pago realizado no puede estar vacío.")
    private String pagoRealizados;

    /** The saldo pendiente. */
    // Saldo pendiente por pagar.
    @NotBlank(message = "El saldo pendiente no puede estar vacío.")
    @Size(min = 1, max = 6, message = "El saldo pendiente debe tener hasta 6 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El saldo pendiente debe contener numeros del 1 al 9")
    private double saldoPendiente;

    /** The costo. */
    // Costo total de la factura.
    @NotBlank(message = "El costo no puede estar vacío.")
    @Size(min = 1, max = 6, message = "El costo debe tener hasta 6 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El costo debe contener numeros del 1 al 9")
    private double costo;
    
    
    /** The citas. */
    @OneToMany(mappedBy="factura", cascade = CascadeType.ALL)
	private List<Cita> citas=new ArrayList<>();
}

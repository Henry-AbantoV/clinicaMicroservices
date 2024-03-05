/*
 * @file HistorialMedico.java;
 * @Autor Henry AV (c)2024
 * @Created 5 mar 2024,0:36:03
 */
<<<<<<< HEAD

=======
>>>>>>> 5a45278ea137551ce211d440fce8777cd3988110
package edu.unc.clinica.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
//TODO: Auto-generated Javadoc
/**
 * La clase Historial Medico representa un historial medico en el sistema. Esta clase está
 * marcada con la anotación @Entity para indicar que es una entidad de base de
 * datos. También utiliza las anotaciones Lombok @Data para generar
 * automáticamente los métodos getter, setter, toString, equals y hashCode.
=======

// TODO: Auto-generated Javadoc
/**
 * The Class HistorialMedico.
 */
@Entity
@Data
public class HistorialMedico {
	
	/** The id historial medico. */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idHistorialMedico;
	
	/**
     * Fecha en la que se registra el historial médico.
     */
	@Temporal(TemporalType.DATE)
	@PastOrPresent(message = "La fecha debe ser anterior o  al dia de hoy")
    private Date fechaRegistro;
	
	/**
    * Diagnósticos previos del paciente.
    */
	private String diagnosticosAnteriores;
	
	/**
     * Tratamientos médicos previos del paciente.
     */
	@NotBlank(message = "Los tratamientos previos no deben estar en blanco")
    private String tratamientosPrevios;
	 
	/** The procedimientos realizados. */
	@NotBlank(message = "Los procedimientos realizados no deben estar en blanco")
    private String procedimientosRealizados;
	 
	/** The medicamentos recetados. */
	@NotBlank(message = "Los medicamentos a recetar no deben estar en blanco")
    private String medicamentosRecetados;
	 
	/** The Resultados pruebas medicas. */
	@NotBlank(message = "Los resultados de las pruebas medicas no deben estar en blanco")
    private String ResultadosPruebasMedicas;
    
    
    /** The paciente. */
    @OneToOne
    @JoinColumn(name="id_Paciente")
    private Paciente paciente;
  
}

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

@Entity
@Data
public class HistorialMedico {
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
	 
	@NotBlank(message = "Los procedimientos realizados no deben estar en blanco")
    private String procedimientosRealizados;
	 
	@NotBlank(message = "Los medicamentos a recetar no deben estar en blanco")
    private String medicamentosRecetados;
	 
	@NotBlank(message = "Los resultados de las pruebas medicas no deben estar en blanco")
    private String ResultadosPruebasMedicas;
    
    
    @OneToOne
    @JoinColumn(name="id_Paciente")
    private Paciente paciente;
  
}

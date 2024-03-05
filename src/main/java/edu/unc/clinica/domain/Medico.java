/*
 * @file Medico.java;
 * @Autor YerssonC.D (c)2024
 * @Created 5 mar 2024,0:27:00
 */
package edu.unc.clinica.domain;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * La clase Medico representa a un profesional médico en el sistema.
 * Esta clase utiliza la anotación Lombok @Data para generar automáticamente los métodos getter, setter, toString, equals y hashCode.
 * Está marcada con la anotación @Entity para indicar que es una entidad de base de datos.
 */
@Data
@Entity
public class Medico {
	
	// Identificador único del médico.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMedico;
	
	// Nombre del médico.
	@NotBlank(message = "El nombre del Dr. no deben estar en blanco")
	private String nombre;
	
	// Apellidos del médico.
	@NotBlank(message = "Los apellidos del Dr. no deben estar en blanco")
	private String apellidos;
	
	// Número de teléfono del médico.
	@NotBlank(message = "El número de teléfono no puede estar vacío.")
	@Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 caracteres.")
	@Pattern(regexp = "^[0-9]*$", message = "El telefono debe contener numeros del 1 al 9")
	private String telefono;
	
	// Horario de atención del médico.
	@NotBlank(message = "El horario de atencion del Dr. no puede estar vacío.")
	private String horario;
	
	// Correo electrónico del médico.
	@Column(unique=true)
	@Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 30, message = "El email debe tener max de 30 caracteres.")
	private String correoElectronico;

	// Relación muchos a muchos con la clase Paciente, mapeada por el campo "medicos" en la clase Paciente.
	@ManyToMany(mappedBy="medicos")
	private List<Paciente>pacientes= new ArrayList<>(); 
	
	// Relación muchos a uno con la clase Especialidad, mapeada por el campo "medicos" en la clase Especialidad.
	@ManyToOne
    @JoinColumn(name="medicos")
   	private Especialidad especialidad;
}

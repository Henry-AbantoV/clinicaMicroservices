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

@Data
@Entity
public class Medico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMedico;
	
	@NotBlank(message = "El nombre del Dr. no deben estar en blanco")
	private String nombre;
	
	@NotBlank(message = "Los apellidos del Dr. no deben estar en blanco")
	private String apellidos;
	
	@NotBlank(message = "El número de teléfono no puede estar vacío.")
	@Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 caracteres.")
	@Pattern(regexp = "^[0-9]*$", message = "El telefono debe contener numeros del 1 al 9")
	private String telefono;
	
	@NotBlank(message = "El horario de atencion del Dr. no puede estar vacío.")
	private String horario;
	
	@Column(unique=true)
	@Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 30, message = "El email debe tener max de 30 caracteres.")
	private String correoElectronico;

	@ManyToMany(mappedBy="medicos")
	private List<Paciente>pacientes= new ArrayList<>(); 
	
	@ManyToOne
    @JoinColumn(name="id_Medico")
   	private Especialidad especialidad;
}

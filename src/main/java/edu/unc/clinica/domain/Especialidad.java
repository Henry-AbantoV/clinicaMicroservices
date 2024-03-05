package edu.unc.clinica.domain;

//Importa las clases necesarias
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Especialidad {
	// Identificador único generado automáticamente para la especialidad
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEspecialidad;
	
	// Anotación que especifica que el nombre de la especialidad no puede estar en blanco
	@NotBlank(message = "El nombre de la especialidad no puede estar vacío.")
	private String nombreEsp;
	
	// Anotación que ignora la serialización de la lista de médicos al formato JSON
	@JsonIgnore
	
	// Relación uno a muchos con la clase Medico, mapeada por el campo "especialidad" en la clase Medico
	@OneToMany(mappedBy="especialidad")
	private List<Medico> medicos=new ArrayList<>();
	// Constructor por defecto
	// (Lombok genera automáticamente el constructor con todos los parámetros)
}

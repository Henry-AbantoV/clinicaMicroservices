
/*
 * @file Especialidad.java;
<<<<<<< HEAD
 * @Autor Daniela Torres (c)2024
 * @Created 5 mar 2024,0:31:12
=======
* @Autor Fernando C.J. (c)2024
 * @Created 5 mar. 2024,00:39:05
>>>>>>> 5a45278ea137551ce211d440fce8777cd3988110
 */

package edu.unc.clinica.domain;

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
//Anotación que indica que esta clase es una entidad JPA
@Entity
//Anotación de Lombok que agrega automáticamente los métodos getter, setter, equals, hashCode y toString
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
	
}

package edu.unc.clinica.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Especialidad {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEspecialidad;
	
	@NotBlank(message = "El nombre de la especialidad no puede estar vacío.")
	private String nombreEsp;
	
	@OneToMany(mappedBy="especialidad", cascade = CascadeType.ALL)
	private List<Medico> medicos=new ArrayList<>();

}

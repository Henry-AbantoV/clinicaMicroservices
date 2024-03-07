/*
 * @file Paciente.java;
 * @Autor Daniela Torres (c)2024
 * @Created 4 mar 2024,23:51:53
 */
package edu.unc.clinica.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idPaciente")
public class Paciente {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;

    // Nombres del paciente.
	@NotBlank(message = "El nombre del paciente  no puede estar vacío.")
    private String nombres;

    // Apellidos del paciente.
	@NotBlank(message = "Los apellidos del paciente  no puede estar vacío.")
    private String apellidos;

    // Fecha de nacimiento del paciente.
    @Temporal(TemporalType.DATE)    
    @PastOrPresent(message = "La fecha debe ser aanterior al dia de hoy")    
    @Past(message = "La fecha de inscripción debe ser en el pasado")    
    private Date fechaNacimiento;

    //Género del paciente.
    @NotBlank(message = "El genero del paciente  no puede estar vacío.")    
    private String genero;

    // DNI del paciente.
    @Column(unique = true)
    @NotBlank(message = "El Documento de Identidad no puede estar vacío.")    
    @Size(min = 8, max = 8, message = "El Documento de Identidad debe tener entre 8 caracteres.")    
    @Pattern(regexp = "^[0-9]*$", message = "El Documento de Identidad solo puede contener números.")    
    private String dni;

    // Dirección del paciente.
    @NotBlank(message = "La direccion del cliente es obigatoria")    
    private String direccion;

    // Número de teléfono del paciente.
    @NotBlank(message = "El número de teléfono no puede estar vacío.")    
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 caracteres.")    
    @Pattern(regexp = "^[0-9]*$", message = "El telefono debe contener numeros del 1 al 9")    
    private String telefono;

    // Correo electrónico del paciente.
    @Column(unique=true)
    @Email(message = "El formato del correo electrónico no es válido")    
    @Size(max = 30, message = "El email debe tener max de 30 caracteres.")    
    private String correoElectronico;
    
    //Una lista de citas asociadas a paciente.
    @OneToMany (mappedBy = "paciente")
    private List<Cita> citas = new ArrayList<>();

   //Una lista de médicos asociados a este paciente.
   @ManyToMany
   @JoinTable(
		  name="Consulta",
		  joinColumns = @JoinColumn(name = "medico_id"),
		  inverseJoinColumns = @JoinColumn(name = "paciente_id"))
   private List<Medico> medicos = new ArrayList<>();        
    
    //Historial médico asociado a paciente.
    @JsonIgnore    
    @OneToOne(mappedBy="paciente")    
    private HistorialMedico historialMedico;  

}

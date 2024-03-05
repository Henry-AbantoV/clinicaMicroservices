package edu.unc.clinica.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


/**
 * La clase Cita representa una cita médica en el sistema.
 * Esta clase está marcada con la anotación @Entity para indicar que es una entidad de base de datos.
 * También utiliza las anotaciones Lombok @Data para generar automáticamente los métodos getter, setter, toString, equals y hashCode.
 * La anotación @JsonIdentityInfo se utiliza para manejar referencias circulares durante la serialización JSON.
 */
@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idCita")
public class Cita {

	// Identificador único de la cita.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    // Fecha y hora de la cita.
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "La fecha debe ser anterior al dia de hoy")
    @Past(message = "La fecha de inscripción debe ser en el pasado")
    private Date fechaHoraCita;

    // Motivo de la cita.
    @NotBlank(message = "El motivo no puede estar vacío.")
    private String motivoCita;

    // Estado de la cita (true si está confirmada, false si está pendiente o cancelada).
    @NotBlank(message = "El estado de la cita no puede estar vacío.")
    private String estadoCita;

    @ManyToOne
    @JoinColumn(name="id_Factura")
   	private Factura factura;
       
    @ManyToOne
    @JoinColumn(name="id_Paciente")
    private Paciente paciente;
    
}

package edu.unc.clinica.services;

import java.util.List;


import edu.unc.clinica.domain.Paciente;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;

public interface PacienteService {

	List<Paciente> listarPacientes();
	
	Paciente buscarPacienteById(Long IdPacient) throws EntityNotFoundException;
	Paciente grabarPaciente (Paciente paciente) throws IllegalOperationException;
	Paciente actualizarPaciente(Long id, Paciente paciente) throws EntityNotFoundException, IllegalOperationException;
	void eliminarPaciente(Long idPaciente) throws EntityNotFoundException, IllegalOperationException;
	Paciente asignarCita(Long idPaciente, Long IdCita) throws EntityNotFoundException, IllegalOperationException;
	
	Paciente asignarHistorial(Long idPaciente, Long idHistorial)  throws EntityNotFoundException, IllegalOperationException;
}

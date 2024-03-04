package edu.unc.clinica.services;

import java.util.List;
import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;


public interface CitaService {

	List<Cita> listarCitas();
	
	Cita buscarCitabyId(Long IdCita) throws EntityNotFoundException;
	Cita grabarCita (Cita cita) throws IllegalOperationException;
	Cita actualizarCita(Long id, Cita cita) throws EntityNotFoundException, IllegalOperationException;
	void eliminarCita(Long IdCita) throws EntityNotFoundException, IllegalOperationException;
	
	Cita asignarFactura(Long IdCita, Long IdFactura) throws EntityNotFoundException, IllegalOperationException;
}

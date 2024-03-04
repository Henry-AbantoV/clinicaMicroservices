package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import edu.unc.clinica.domain.HistorialMedico;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.IllegalOperationException;

public interface HistorialService {
	List<HistorialMedico> listarHistorial();
	Optional<HistorialMedico> buscarPorIdHistorial(Long idHistorial)throws EntityNotFoundException;
	HistorialMedico grabarHistorial(HistorialMedico historial)throws IllegalOperationException;
	HistorialMedico actualizarHistorial(Long idHistorial, HistorialMedico historial) throws EntityNotFoundException, IllegalOperationException;
	void eliminarHistorial(Long idHistorial) throws EntityNotFoundException, IllegalOperationException;
}

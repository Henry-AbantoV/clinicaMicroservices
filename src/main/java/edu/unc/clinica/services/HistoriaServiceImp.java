package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.HistorialMedico;
import edu.unc.clinica.domain.Paciente;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.HistorialMedicoRepository;
import edu.unc.clinica.repositories.PacienteRepository;

@Service
public class HistoriaServiceImp implements HistorialService {
	@Autowired
	private HistorialMedicoRepository historialR;
	
	@Autowired
	private PacienteRepository pacienteR;

	@Override
	@Transactional
	public List<HistorialMedico> listarHistorial() {
		return (List<HistorialMedico>)historialR.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public HistorialMedico buscarPorIdHistorial(Long idHistorial) throws EntityNotFoundException {
		
		Optional<HistorialMedico> historial=historialR.findById(idHistorial);
		if (historial.isEmpty())
			throw new EntityNotFoundException("El historial medico con el id proporcionado no existe en la BD");
		return historial.get();
	}

	@Override
	@Transactional
	public HistorialMedico grabarHistorial(HistorialMedico historial) throws IllegalOperationException {
		return historialR.save(historial);
	}

	@Override
	public void eliminarHistorial(Long idHistorial) throws EntityNotFoundException, IllegalOperationException {
		historialR.deleteById(idHistorial);
	}
	
	@Override
	@Transactional
	public HistorialMedico actualizarHistorial(Long idHistorial, HistorialMedico historial) throws EntityNotFoundException, IllegalOperationException{
		Optional<HistorialMedico> historialEntity = historialR.findById(idHistorial);
		if(historialEntity.isEmpty())
			throw new EntityNotFoundException("El id proporcionado no encontrado para actualizarlo");
			
		historial.setIdHistorialMedico(idHistorial);		
		return historialR.save(historial);		
	}
	
	@Override
	@Transactional
	public HistorialMedico asignarHistorial(Long idHistorial,Long idPaciente) throws EntityNotFoundException, IllegalOperationException {

		try {
			Paciente pacienteEntity =  pacienteR.findById(idPaciente).orElseThrow(
					()->new EntityNotFoundException("El id del paciente no existe en la BD")
					);
			HistorialMedico histEntity = historialR.findById(idHistorial).orElseThrow(
					()->new EntityNotFoundException("El id del historial medico aun no existe en la BD")
					);
			if (pacienteEntity.getHistorialMedico()== null) {
				histEntity.setPaciente(pacienteEntity);
				return historialR.save(histEntity);
	        } else {
	            throw new IllegalOperationException("El paciente ya tiene asignado un historial");
	        }
			}catch (Exception e) {
		        throw new IllegalOperationException("Error durante la asignación de historial");
		    }
	
	}

}

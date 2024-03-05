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
	public Optional<HistorialMedico> buscarPorIdHistorial(Long idHistorial) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return historialR.findById(idHistorial);
	}

	/*@Override
	@Transactional
	public Optional<HistorialMedico> buscarPorDniPaciente(String dniPaciente) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return historialR.findByPaciente_Dni(dniPaciente);
	}*/

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
			throw new EntityNotFoundException(ErrorMessage.CITA_NOT_FOUND);
			
		historial.setIdHistorialMedico(idHistorial);		
		return historialR.save(historial);		
	}
	
	@Override
	@Transactional
	public HistorialMedico asignarHistorial(Long idHistorial,Long idPaciente) throws EntityNotFoundException, IllegalOperationException {

		try {
			Paciente pacienteEntity =  pacienteR.findById(idPaciente).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.PACIENTE_NOT_FOUND)
					);
			HistorialMedico histEntity = historialR.findById(idHistorial).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.HISTORIAL_MEDICO_NOT_FOUND)
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

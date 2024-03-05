package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.Cita;
import edu.unc.clinica.domain.HistorialMedico;
import edu.unc.clinica.domain.Paciente;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.CitaRepository;
import edu.unc.clinica.repositories.HistorialMedicoRepository;
import edu.unc.clinica.repositories.PacienteRepository;

@Service
public class PacienteServiceImp implements PacienteService{

	@Autowired
	private PacienteRepository pacientR;
	
	@Autowired
	private CitaRepository citaR;
	
	@Autowired
	private HistorialMedicoRepository histoR;
	
	@Override
	public List<Paciente> listarPacientes() {
		return (List<Paciente>)pacientR.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Paciente buscarPacienteById(Long IdPacient) throws EntityNotFoundException {
		Optional<Paciente> paciente=pacientR.findById(IdPacient);
		return paciente.get();
	}

	@Override
	@Transactional
	public Paciente grabarPaciente(Paciente paciente) throws IllegalOperationException {
		return pacientR.save(paciente);
	}

	@Override
	@Transactional
	public Paciente actualizarPaciente(Long id, Paciente paciente)throws EntityNotFoundException, IllegalOperationException {
		Optional<Paciente> pacEntity = pacientR.findById(id);
		if(pacEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.PACIENTE_NOT_FOUND);
			
		paciente.setIdPaciente(id);		
		return pacientR.save(paciente);
	}

	@Override
	@Transactional
	public void eliminarPaciente(Long IdPacient) throws EntityNotFoundException, IllegalOperationException {
		pacientR.deleteById(IdPacient);
		
	}

	@Override
	@Transactional
	public Paciente asignarCita(Long IdPacient, Long IdCita) throws EntityNotFoundException, IllegalOperationException {

		try {
			Paciente pacienteEntity =  pacientR.findById(IdPacient).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.PACIENTE_NOT_FOUND)
					);
			Cita citaEntity = citaR.findById(IdCita).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.CITA_NOT_FOUND)
					);
			if (citaEntity.getPaciente()== null) {
				citaEntity.setPaciente(pacienteEntity);
				return pacientR.save(pacienteEntity);
	        } else {
	            throw new IllegalOperationException("El paciente ya tiene asignada una cita");
	        }
			}catch (Exception e) {
		        throw new IllegalOperationException("Error durante la asignación de cita");
		    }
	
	}
	
	@Override
	@Transactional
	public Paciente asignarHistorial(Long idPaciente, Long idHistorial) throws EntityNotFoundException, IllegalOperationException {

		try {
			Paciente pacienteEntity =  pacientR.findById(idPaciente).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.PACIENTE_NOT_FOUND)
					);
			HistorialMedico histEntity = histoR.findById(idHistorial).orElseThrow(
					()->new EntityNotFoundException(ErrorMessage.HISTORIAL_MEDICO_NOT_FOUND)
					);
			if (pacienteEntity.getHistorialMedico()== null) {
				pacienteEntity.setHistorialMedico(histEntity);
				return pacientR.save(pacienteEntity);
	        } else {
	            throw new IllegalOperationException("El paciente ya tiene asignado un historial");
	        }
			}catch (Exception e) {
		        throw new IllegalOperationException("Error durante la asignación de historial");
		    }
	
	}

}

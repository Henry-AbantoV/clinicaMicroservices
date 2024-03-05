/*
 * @file MedicoServiceImp.java;
 * @Autor YerssonC.D (c)2024
 * @Created 5 mar 2024,0:32:42
 */
package edu.unc.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unc.clinica.domain.Medico;
import edu.unc.clinica.domain.Paciente;
import edu.unc.clinica.exceptions.EntityNotFoundException;
import edu.unc.clinica.exceptions.ErrorMessage;
import edu.unc.clinica.exceptions.IllegalOperationException;
import edu.unc.clinica.repositories.MedicoRepository;
import edu.unc.clinica.repositories.PacienteRepository;

@Service
public class MedicoServiceImp implements MedicoService {

	
	@Autowired
	private MedicoRepository medicoRep;


	@Autowired
	private PacienteRepository pacienteRep;

	@Override
	public List<Medico> listarMedicos() {
		// TODO Auto-generated method stub
		return medicoRep.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Medico buscarMedicoById(Long idMedico) throws EntityNotFoundException {
		Optional<Medico> medico = medicoRep.findById(idMedico);
		if (medico.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND);
		return medico.get();
	}

	@Override
	public Medico grabarMedico(Medico medico) throws IllegalOperationException {
		// TODO Auto-generated method stub
		return medicoRep.save(medico);
	}

	@Override
	@Transactional
	public Medico actualizarMedico(Long id, Medico medico) throws EntityNotFoundException, IllegalOperationException {
		Optional<Medico> medicoEntity = medicoRep.findById(id);
		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND);
		
		medico.setIdMedico(id);
		return medicoRep.save(medico);

	}

	@Override
	public void eliminarMedico(Long IdMedico) throws EntityNotFoundException, IllegalOperationException {
		medicoRep.deleteById(IdMedico);

	}


	@Override
	public Medico asignarPaciente(Long idMedico, Long idPaciente)
			throws EntityNotFoundException, IllegalOperationException {
		Medico medicoEntity = medicoRep.findById(idMedico)
				.orElseThrow(() -> new EntityNotFoundException("Medico no encontrado con ID: " + idMedico));

		Paciente paciente = pacienteRep.findById(idPaciente)
				.orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + idPaciente));

	
		if (!medicoEntity.getPacientes().contains(paciente)) {
			medicoEntity.getPacientes().add(paciente); 
			medicoRep.save(medicoEntity); 
		} else {
			throw new IllegalOperationException("Este paciente ya estÃ¡ asignado a este mÃ©dico");
		}

		return medicoEntity;
	}

}
